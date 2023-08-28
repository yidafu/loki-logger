# Loki Logger

## 日志格式

```log
timestamp <topic> <hostname/machine id> <pid/thread name> <env> Level (tag name) [key:value] [key:value] - message content

1693020734199 <test-app> <local-hostname> <1234> <dev> INFO (TestTag) [uuid:1234567] [biz:test-module] [userId:1234] - log content
```

timestamp 时间戳

`<.*>` 这是固定的标签。
+ topic 区分应用
+ hostname 区分机器
+ pid 进程区分
+ 应用环境

Level 日志等级

`(\w+)`是日志标签

`[key:value]`是自定义标签


