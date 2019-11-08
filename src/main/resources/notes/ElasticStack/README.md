# 1. Elastic Search 学习

## 1.1 基础

### 1.1.1 原子性更新操作

ES提供两种方式来进行原子性更新，两者原理一致：在更新时检查是否与指定的字段一致，如果一致则更新，如果不一致则抛出错误，由调用程序决定如何进行下一步处理，两种方式的区别在于使用的字段不一致：可以使用内部或者外部字段，此方法同时支持UPDATE

#### 1.1.1.1 使用内部的version字段

通过在PUT请求时，带上更新前版本号，可以保证原子修改：

> PUT /website/blog/3?version=1

调用时，ES只有在数据库中version与调用时指定的version参数**相同**时进行更新

#### 1.1.1.2 使用外部的字段控制

相当于手动指定内部的version而不使用内部的自增version，调用时，ES只有在数据库中version**小于**调用时指定的version参数时进行更新。

##### 1.1.1.2.1 创建一份包含外部版本号的文档

> PUT /website/blog/4?version=20190919010101&version_type=external

注意，所有的version都可以被这种带version_type参数的方法强制修改为更大的数，version数值边界为{数据库当前version，Java中long类型的最大值}

##### 1.1.1.2.2 调用者1进行更新操作

> PUT /website/blog/4?version=20190919010201&version_type=external

由于数据库中version \< 参数version，更新成功

##### 1.1.1.2.3 调用者2进行更新操作

> PUT /website/blog/4?version=20190919010102&version_type=external

由于数据库中version > 参数version，更新失败，返回报错信息

### 1.2 Groovy脚本

```json
{
    "script": {
        "source": "ctx._source.tags.add(params.new_tag)",
        "params": {
            "new_tag": "java"
        }
    }
}
```

### 1.3 批量请求

#### 1.3.1 mget

##### 1.3.1.1 批量请求

> GET /website/blog/_mget

```json
{
    "docs": [
        {
            "_type":"notexist",
            "_id": 1
        },
        {
            "_id": 6,
            "_source": "view_count"
        }
    ]
}
```

注意，第一个Request覆盖了URL中默认的_type

> GET /website/blog/_mget

```json
{
    "ids":["1","6"]
}
```

#### 1.3.2 合并更新

> POST /website/blog/_bulk

```json
{"create":{"_id":7}}
{"title":"bulk added","view_count":0}
{"update":{"_id":6}}
{"doc":{"title":"bulk update"}}
{"index":{"_id":8}}
{"title":"bulk indexed","view_count":0}
{"delete":{"_id":3}}
```

## 1.2 查询

### 1.2.1 analyze接口

> POST /analyze

```json
{
    "analyzer":"standard",
    "text":"I'm fine,thank you my-darling!thanks"
}
```

### 1.2.2 DSL查询

> GET /_search

```json
{
    "query": {
        "bool": {
            "must": {
                "match": {
                    "tweet": "elasticsearch"
                }
            },
            "must_not": [
                {
                    "match": {
                        "tweet": "I"
                    }
                },
                {
                    "match": {
                        "name": "john"
                    }
                }
            ],
            "should": {
                "match": {
                    "tweet": "full text"
                }
            },
            "filter": [
                {
                    "terms": {
                        "tweet": [
                            "scale",
                            "asdf"
                        ]
                    }
                },
                {
                    "range": {
                        "date": {
                            "lt": 1568896519467
                        }
                    }
                }
            ]
        }
    }
}
```

### 1.2.3 DSL排序

> GET /gb/_search

```json
{
    "query": {
        "bool": {
            "must": {
                "match": {
                    "tweet": "elasticsearch"
                }
            }
        }
    },
    "sort": {
        "date": {
            "order": "asc"
        },
        "_score":{
            "order":"desc"
        }
    }
}
```

### 1.2.4 修改Mappping,添加多值

> PUT /gb/_mapping/tweet

```json
{
    "properties": {
        "tweet": {
                "type":"text",
            "fields": {
                "raw": {
                    "type": "keyword"
                }
            }
        }
    }
}
```

### 1.2.5 查看Explain

> GET gb/_search?&explain=true

### 1.2.6 查询过程中的结果震荡

在查询过程中，由于主分片和复制分片不强制要求顺序一致，因此，假如对某些文档使用x字段排序，当这些文档的x值相同时，可能发生在主分片和副分片排序结果不一致问题，导致相同的查询在多次查询时返回不同的结果。解决办法是使用_preferrence字段，将查询用户的ID映射到某个分片上。

### 1.2.7 路由选择

1. 创建
2. 使用

### 1.2.8 Scroll

1. 创建
    > POST gb/_search?scroll=1m

    ```json
    {
        "query": {
            "match_all": {}
        },
        "size": 2
    }
    ```

    返回的响应里有_scroll_id

2. 滚动
    > POST /_search/scroll

    ```json
    {
        "scroll":"1m",
        "scroll_id":"sadf"
    }
    ```

再次滚动时需要提供上一次滚动返回的scroll_id

## 1.3 建立索引

### 1.3.1 自定义索引设置

> PUT /custom_index

```json
{
    "settings":{
        "number_of_shards":3,
        "number_of_replicas":1,
        "analysis":{
            "char_filter":{
                "&_to_and":{
                    "type":"mapping",
                    "mappings":["&=> and"]
                }
            },
            "filter":{
                "kk_stop_filter":{
                    "type":"stop",
                    "stopwords":["the","a"]
                }
            },
            "analyzer":{
                "kk_analyzer":{
                    "type":"custom",
                    "char_filter":["html_strip","&_to_and"],
                    "tokenizer":"standard",
                    "filter":["lowercase","kk_stop_filter"]
                }
            }
        }
    }
}
```

### 1.3.2 在索引内部的某个字段上使用自定义的analyzer

> PUT /custom_index/_mapping/custom_type

```json
{
    "properties":{
        "title":{
            "type":"text",
            "analyzer":"kk_analyzer"
        },
        "content":{
            "type":"text",
            "analyzer":"standard"
        }
    }
}
```

### 1.3.3 字段设置

#### 1.3.3.1 不允许动态增加字段

> PUT /custom_index/_mapping/custom_type

```json
{
    "dynamic": "strict",
    "properties": {
        "title": {
            "type": "text",
            "analyzer": "kk_analyzer"
        },
        "content": {
            "type": "text",
            "analyzer": "standard"
        }
    }
}
```

## 1.4 过滤器

```json
{
    "query": {
        "bool": {
            "filter": {
                "bool": {
                    "must": [
                        {
                            "bool": {
                                "should": [
                                    {
                                        "term": {
                                            "price": 20
                                        }
                                    },
                                    {
                                        "term": {
                                            "productID": "XHDK-A-1293-#fJ3"
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            "bool": {
                                "must_not": {
                                    "term": {
                                        "price": 30
                                    }
                                }
                            }
                        }
                    ]
                }
            }
        }
    }
}
```

注意事项：

1. 一元的查询："bool","filter","must_not",值为key是关键字的字典，二元运算"must"和”should“，值为key是关键字的字典的**数组**

