-- 创建健康数据表
CREATE TABLE user_health_metrics (
    -- 使用 BIGSERIAL (自增长长整型) 作为主键，适合大数据量
    id BIGSERIAL PRIMARY KEY,
    
    -- 用户 ID，建议添加索引以提高查询速度
    user_id VARCHAR(50) NOT NULL,
    
    -- 步数：使用 INTEGER 即可（一般人一天走不到 21 亿步）
    steps INTEGER DEFAULT 0,
    
    -- 距离：单位建议统一为“米”，使用 NUMERIC 保证精度
    -- 比如 5.25 公里存为 5250.00
    distance_meters NUMERIC(10, 2) DEFAULT 0.00,
    
    -- 卡路里：使用 NUMERIC 保证精度
    calories_kcal NUMERIC(10, 2) DEFAULT 0.00,
    
    -- 数据记录所属的日期（例如：2023-10-27）
    -- 方便按天进行聚合查询
    log_date DATE NOT NULL,
    
    -- 该条记录创建的时间（系统自动生成）
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    -- 约束：同一个用户在同一天原则上只应有一条汇总记录（可选）
    UNIQUE(user_id, log_date)
);

-- 为 user_id 和 log_date 创建复合索引，大幅提升查询性能
CREATE INDEX idx_user_health_date ON user_health_metrics (user_id, log_date);