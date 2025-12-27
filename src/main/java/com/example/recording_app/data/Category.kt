package com.example.recording_app.data

data class Category(
    val id: String,
    val name: String,
    val icon: String,
    val color: Long,
    val type: RecordType
)

object DefaultCategories {
    fun getExpenseCategories(): List<Category> = listOf(
        Category("1", "餐饮", "🍔", 0xFFEF4444, RecordType.EXPENSE),
        Category("2", "交通", "🚗", 0xFF3B82F6, RecordType.EXPENSE),
        Category("3", "购物", "🛍️", 0xFF8B5CF6, RecordType.EXPENSE),
        Category("4", "娱乐", "🎬", 0xFFEC4899, RecordType.EXPENSE),
        Category("5", "医疗", "🏥", 0xFF10B981, RecordType.EXPENSE),
        Category("6", "教育", "📚", 0xFFF59E0B, RecordType.EXPENSE),
        Category("7", "住房", "🏠", 0xFF6366F1, RecordType.EXPENSE),
        Category("8", "其他", "📝", 0xFF6B7280, RecordType.EXPENSE),
    )

    fun getIncomeCategories(): List<Category> = listOf(
        Category("9", "工资", "💰", 0xFF10B981, RecordType.INCOME),
        Category("10", "奖金", "🎁", 0xFFF59E0B, RecordType.INCOME),
        Category("11", "投资", "📈", 0xFF3B82F6, RecordType.INCOME),
        Category("12", "其他收入", "💵", 0xFF6366F1, RecordType.INCOME),
    )

    fun getAllCategories(): List<Category> = getExpenseCategories() + getIncomeCategories()
}

