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

### 1.3 合并请求

#### 1.3.1 mget

##### 1.3.1.1 合并请求

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

