import boto3
import os
import urllib.parse
from PIL import Image
import io

s3 = boto3.client('s3')

MAX_SIZE_BYTES = int(os.environ.get('MAX_SIZE_BYTES', 20 * 1024 * 1024))  # 20MB
THUMBNAIL_SIZE = (300, 300)
ALLOWED_EXTENSIONS = {'.jpg', '.jpeg', '.png', '.webp'} # 이미지 확장자만 허용


def handler(event, context):
    results = []
    for record in event['Records']:
        bucket = record['s3']['bucket']['name']
        key = urllib.parse.unquote_plus(record['s3']['object']['key'])
        size = record['s3']['object']['size']

        print(f"Processing: {key}, size: {size} bytes")

        # 썸네일이 트리거한 경우 무시
        if key.startswith("thumbnail/"):
            results.append({"status": "skipped", "key": key})
            continue

        # 이미지 파일이 아닌 경우 무시
        ext = os.path.splitext(key)[1].lower()
        if ext not in ALLOWED_EXTENSIONS:
            print(f"Not an image file, skipping: {key}")
            results.append({"status": "skipped", "key": key})
            continue

        # 크기 초과 시 삭제
        if size > MAX_SIZE_BYTES:
            print(f"File too large ({size} bytes), deleting: {key}")
            s3.delete_object(Bucket=bucket, Key=key)
            results.append({"status": "deleted", "reason": "file too large", "key": key})
            continue

        # 썸네일 생성
        response = s3.get_object(Bucket=bucket, Key=key)
        image_data = response['Body'].read()

        image = Image.open(io.BytesIO(image_data))
        image.thumbnail(THUMBNAIL_SIZE)

        buffer = io.BytesIO()
        image.save(buffer, format=image.format or "JPEG")
        buffer.seek(0)

        # feed-images/uuid.png -> thumbnail/feed-images/uuid.png
        thumbnail_key = "thumbnail/" + key

        s3.put_object(
            Bucket=bucket,
            Key=thumbnail_key,
            Body=buffer,
            ContentType=f"image/{(image.format or 'jpeg').lower()}"
        )

        print(f"Thumbnail created: {thumbnail_key}")
        results.append({"status": "success", "thumbnail_key": thumbnail_key})

    return results
