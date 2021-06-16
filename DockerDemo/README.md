# 命令

## 建造

`docker build -t DockerDemo:1.0 .`
_--tag, -t: 镜像的名字及标签，通常 name:tag 或者 name 格式；可以在一次构建中为一个镜像设置多个标签_
_别忘记最后的"."，给COPY ADD等命令中的使用的文件指定上下文_

## 运行

`docker run -itd DockerDemo:1.0`
_-i: 以交互模式运行容器_
_-t: 为容器重新分配一个伪输入终端_
_-d: 后台运行容器_
