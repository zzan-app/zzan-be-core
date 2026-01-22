variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "ap-northeast-2" # 서울 리전
}

variable "project_name" {
  description = "Project name for resource naming"
  type        = string
  default     = "zzan"
}

variable "key_name" {
  description = "Existing AWS Key Pair name"
  type        = string
  default     = "zzan-rsa-key"
}

variable "ami_id" {
  description = "AMI ID for EC2 instances"
  type        = string
  default     = "ami-0c9c942bd7bf113a2" # Ubuntu 22.04 LTS (ap-northeast-2)
}

variable "nginx_instance_type" {
  description = "Instance type for Nginx server"
  type        = string
  default     = "t3.micro"
}

variable "main_instance_type" {
  description = "Instance type for Main server (Spring Boot + DB + Redis)"
  type        = string
  default     = "t3.small"
}

variable "fastapi_instance_type" {
  description = "Instance type for FastAPI server"
  type        = string
  default     = "t3.micro"
}
