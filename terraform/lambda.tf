locals {
  pillow_layer_arn = "arn:aws:lambda:${var.aws_region}:770693421928:layer:Klayers-p312-Pillow:10"

  lambda_functions = {
    image_processor = {
      handler     = "index.handler"
      runtime     = "python3.12"
      description = "이미지 크기 검증 및 썸네일 생성"
      environment = {
        MAX_SIZE_BYTES = "20971520" # 20MB
      }
    }
  }
}

# Lambda 코드 압축
data "archive_file" "lambda" {
  for_each    = local.lambda_functions
  type        = "zip"
  source_dir  = "${path.module}/lambda/${each.key}"
  output_path = "${path.module}/lambda/${each.key}.zip"
}

# IAM Role - Lambda
resource "aws_iam_role" "lambda" {
  name = "${var.project_name}-lambda-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      }
    ]
  })
}

# IAM Policy - Lambda가 S3 접근
resource "aws_iam_role_policy" "lambda_s3" {
  name = "${var.project_name}-lambda-s3-policy"
  role = aws_iam_role.lambda.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "s3:GetObject",
          "s3:PutObject",
          "s3:DeleteObject"
        ]
        Resource = "arn:aws:s3:::${var.bucket_name}/*"
      }
    ]
  })
}

# IAM Policy - CloudWatch 로그
resource "aws_iam_role_policy_attachment" "lambda_logs" {
  role       = aws_iam_role.lambda.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

# Lambda Function
resource "aws_lambda_function" "functions" {
  for_each         = local.lambda_functions
  filename         = data.archive_file.lambda[each.key].output_path
  source_code_hash = data.archive_file.lambda[each.key].output_base64sha256
  function_name    = "${var.project_name}-${each.key}"
  role             = aws_iam_role.lambda.arn
  handler          = each.value.handler
  runtime          = each.value.runtime
  description      = each.value.description
  timeout          = 30
  memory_size      = 512

  layers = [local.pillow_layer_arn]

  environment {
    variables = each.value.environment
  }

  tags = {
    Name = "${var.project_name}-${each.key}"
  }
}

# S3 트리거
resource "aws_s3_bucket_notification" "image_upload" {
  bucket = var.bucket_name

  lambda_function {
    lambda_function_arn = aws_lambda_function.functions["image_processor"].arn
    events = ["s3:ObjectCreated:*"]
  }

  depends_on = [aws_lambda_permission.s3_invoke]
}

# Lambda가 S3에서 호출될 수 있도록 권한 부여
resource "aws_lambda_permission" "s3_invoke" {
  for_each      = local.lambda_functions
  statement_id  = "AllowS3Invoke-${each.key}"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.functions[each.key].function_name
  principal     = "s3.amazonaws.com"
  source_arn    = "arn:aws:s3:::${var.bucket_name}"
}
