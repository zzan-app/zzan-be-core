terraform {
    required_version = ">= 1.0.0"

    required_providers {
        aws = {
            source  = "hashicorp/aws"
            version = "~> 5.0"
        }
    }
}

provider "aws" {
    region = var.aws_region
}

# VPC
resource "aws_vpc" "main" {
    cidr_block           = "10.0.0.0/16"
    enable_dns_hostnames = true
    enable_dns_support   = true

    tags = {
        Name = "${var.project_name}-vpc"
    }
}

# Internet Gateway
resource "aws_internet_gateway" "main" {
    vpc_id = aws_vpc.main.id

    tags = {
        Name = "${var.project_name}-igw"
    }
}

# Public Subnet
resource "aws_subnet" "public" {
    vpc_id                  = aws_vpc.main.id
    cidr_block              = "10.0.1.0/24"
    availability_zone       = "${var.aws_region}a"
    map_public_ip_on_launch = true

    tags = {
        Name = "${var.project_name}-public-subnet"
    }
}

# Route Table
resource "aws_route_table" "public" {
    vpc_id = aws_vpc.main.id

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.main.id
    }

    tags = {
        Name = "${var.project_name}-public-rt"
    }
}

# Route Table Association
resource "aws_route_table_association" "public" {
    subnet_id      = aws_subnet.public.id
    route_table_id = aws_route_table.public.id
}

# Security Group - Nginx
resource "aws_security_group" "nginx" {
    name        = "${var.project_name}-nginx-sg"
    description = "Security group for Nginx server"
    vpc_id      = aws_vpc.main.id

    ingress {
        description = "HTTP"
        from_port   = 80
        to_port     = 80
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    ingress {
        description = "HTTPS"
        from_port   = 443
        to_port     = 443
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    ingress {
        description = "SSH"
        from_port   = 22
        to_port     = 22
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    egress {
        from_port = 0
        to_port   = 0
        protocol  = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = {
        Name = "${var.project_name}-nginx-sg"
    }
}

# Security Group - Main Server (Spring Boot + DB + Redis)
resource "aws_security_group" "main_server" {
    name        = "${var.project_name}-main-sg"
    description = "Security group for Main server"
    vpc_id      = aws_vpc.main.id

    ingress {
        description = "SSH"
        from_port   = 22
        to_port     = 22
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    ingress {
        description = "Spring Boot from Nginx"
        from_port   = 8000
        to_port     = 8000
        protocol    = "tcp"
        security_groups = [aws_security_group.nginx.id]
    }

    ingress {
        description = "Redis from FastAPI"
        from_port   = 6379
        to_port     = 6379
        protocol    = "tcp"
        security_groups = [aws_security_group.fastapi.id]
    }

    egress {
        from_port = 0
        to_port   = 0
        protocol  = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = {
        Name = "${var.project_name}-main-sg"
    }
}

# Security Group - FastAPI
resource "aws_security_group" "fastapi" {
    name        = "${var.project_name}-fastapi-sg"
    description = "Security group for FastAPI server"
    vpc_id      = aws_vpc.main.id

    ingress {
        description = "SSH"
        from_port   = 22
        to_port     = 22
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    ingress {
        description = "FastAPI from Nginx"
        from_port   = 8000
        to_port     = 8000
        protocol    = "tcp"
        security_groups = [aws_security_group.nginx.id]
    }

    egress {
        from_port = 0
        to_port   = 0
        protocol  = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = {
        Name = "${var.project_name}-fastapi-sg"
    }
}

# 기존 Key Pair 사용

# EC2 Instance - Nginx
resource "aws_instance" "nginx" {
    ami           = var.ami_id
    instance_type = var.nginx_instance_type
    key_name      = var.key_name
    subnet_id     = aws_subnet.public.id
    vpc_security_group_ids = [aws_security_group.nginx.id]

    root_block_device {
        volume_size = 8
        volume_type = "gp3"
    }

    tags = {
        Name = "${var.project_name}-nginx"
        Role = "nginx"
    }
}

# EC2 Instance - Main Server
resource "aws_instance" "main_server" {
    ami           = var.ami_id
    instance_type = var.main_instance_type
    key_name      = var.key_name
    subnet_id     = aws_subnet.public.id
    vpc_security_group_ids = [aws_security_group.main_server.id]

    root_block_device {
        volume_size = 20
        volume_type = "gp3"
    }

    tags = {
        Name = "${var.project_name}-main"
        Role = "main-server"
    }
}

# EC2 Instance - FastAPI
resource "aws_instance" "fastapi" {
    ami           = var.ami_id
    instance_type = var.fastapi_instance_type
    key_name      = var.key_name
    subnet_id     = aws_subnet.public.id
    vpc_security_group_ids = [aws_security_group.fastapi.id]

    root_block_device {
        volume_size = 10
        volume_type = "gp3"
    }

    tags = {
        Name = "${var.project_name}-fastapi"
        Role = "fastapi"
    }
}
