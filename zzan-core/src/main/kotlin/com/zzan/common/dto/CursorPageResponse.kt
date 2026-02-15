package com.zzan.common.dto

data class CursorPageResponse<T>(
    val items: List<T>,
    val nextCursor: String? = null,
    val hasNext: Boolean = false
) {
    companion object {
        fun <T> of(
            items: List<T>,
            size: Int,
            cursorSelector: (T) -> String?
        ): CursorPageResponse<T> {
            val hasNext = items.size > size
            val pagedItems = items.take(size)
            return CursorPageResponse(
                items = pagedItems,
                nextCursor = if (hasNext) pagedItems.lastOrNull()?.let(cursorSelector) else null,
                hasNext = hasNext
            )
        }
    }
}
