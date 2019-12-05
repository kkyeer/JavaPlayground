# mysql中GROUP BY结合排序的写法

## 1. 背景

假设有一张表，数据如下

| time                | dint | dchar | label     | id |
|---------------------|------|-------|-----------|----|
| 2019-12-05 15:02:28 | 99   | now旧 | now       | 1  |
| 2019-12-04 15:02:47 | 55   | yes新 | yesterday | 2  |
| 2019-12-13 15:02:36 | 1    | now新 | now       | 3  |
| 2019-12-03 15:02:31 | 2    | yes旧 | yesterday | 4  |

```sql
DROP TABLE IF EXISTS `t2`;
CREATE TABLE `t2`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `dint` int(5) NULL DEFAULT NULL,
  `dchar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t2
-- ----------------------------
INSERT INTO `t2` VALUES (1, '2019-12-05 15:02:28', 99, 'now旧', 'now');
INSERT INTO `t2` VALUES (2, '2019-12-04 15:02:47', 55, 'yes新', 'yesterday');
INSERT INTO `t2` VALUES (3, '2019-12-13 15:02:36', 1, 'now新', 'now');
INSERT INTO `t2` VALUES (4, '2019-12-03 15:02:31', 2, 'yes旧', 'yesterday');
```

需求是查找各种label下最新的记录，解析成sql就是group by label的记录中取time最大的那条记录

## 2. 正确写法

```sql
SELECT
	t2.* 
FROM
	( SELECT DISTINCT label FROM t2 ) l
	JOIN t2 ON t2.id = ( SELECT id FROM t2 WHERE t2.label = l.label ORDER BY t2.time DESC LIMIT 1 );
```

从目标表中查询到所有的label，再根据label每个查询time最大的row

## 3. 错误写法：

### 子查询:结果错误

```sql
SELECT
	* 
FROM
	( SELECT * FROM t2 ORDER BY time DESC ) a 
GROUP BY
	a.label;
```
