import boto3
import os
from PIL import Image
import io

s3 = boto3.client('s3')

MAX_SIZE_BYTES = int(os.environ.get('MAX_SIZE_BYTES', 20 * 1024 * 1024))  # 20MB
THUMBNAIL_SIZE = (300, 300)


def handler(event, context):
    record = event['Records'][0]
    bucket = record['s3']['bucket']['name']
    key = record['s3']['object']['key']
    size = record['s3']['object']['size']

    print(f"Processing: {key}, size: {size} bytes")

    # 썸네일이 트리거한 경우 무시
    if key.startswith("thumbnail/"):
        return {"status": "skipped"}

    # 크기 초과 시 삭제
    if size > MAX_SIZE_BYTES:
        print(f"File too large ({size} bytes), deleting: {key}")
        s3.delete_object(Bucket=bucket, Key=key)
        return {"status": "deleted", "reason": "file too large"}

    # 썸네일 생성
    response = s3.get_object(Bucket=bucket, Key=key)
    image_data = response['Body'].read()

    image = Image.open(io.BytesIO(image_data))
    image.thumbnail(THUMBNAIL_SIZE)

    if image.mode in ("RGBA", "P"):
        image = image.convert("RGB")

    buffer = io.BytesIO()
    image.save(buffer, format="JPEG", quality=85)
    buffer.seek(0)

    # feed-images/uuid.jpg -> thumbnail/feed-images/uuid.jpg
    thumbnail_key = "thumbnail/" + key

    s3.put_object(
        Bucket=bucket,
        Key=thumbnail_key,
        Body=buffer,
        ContentType="image/jpeg"
    )

    print(f"Thumbnail created: {thumbnail_key}")ㅇ
    return {"status": "success", "thumbnail_key": thumbnail_key}
