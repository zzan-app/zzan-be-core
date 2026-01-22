output "vpc_id" {
  description = "VPC ID"
  value       = aws_vpc.main.id
}

output "public_subnet_id" {
  description = "Public Subnet ID"
  value       = aws_subnet.public.id
}

output "nginx_public_ip" {
  description = "Nginx server public IP"
  value       = aws_instance.nginx.public_ip
}

output "nginx_private_ip" {
  description = "Nginx server private IP"
  value       = aws_instance.nginx.private_ip
}

output "main_server_public_ip" {
  description = "Main server public IP (for DataGrip connection)"
  value       = aws_instance.main_server.public_ip
}

output "main_server_private_ip" {
  description = "Main server private IP"
  value       = aws_instance.main_server.private_ip
}

output "fastapi_public_ip" {
  description = "FastAPI server public IP"
  value       = aws_instance.fastapi.public_ip
}

output "fastapi_private_ip" {
  description = "FastAPI server private IP"
  value       = aws_instance.fastapi.private_ip
}

output "ssh_commands" {
  description = "SSH commands for each server"
  value = {
    nginx   = "ssh -i ~/.ssh/your-key ubuntu@${aws_instance.nginx.public_ip}"
    main    = "ssh -i ~/.ssh/your-key ubuntu@${aws_instance.main_server.public_ip}"
    fastapi = "ssh -i ~/.ssh/your-key ubuntu@${aws_instance.fastapi.public_ip}"
  }
}

output "datagrip_connection" {
  description = "DataGrip connection info for Main server DB"
  value = {
    host          = aws_instance.main_server.public_ip
    postgres_port = 5432
    mysql_port    = 3306
  }
}
