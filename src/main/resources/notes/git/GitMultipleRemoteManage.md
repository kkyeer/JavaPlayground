# Git多个远程分支间核心代码同步探索

## 前言

考虑下面一种场景，某项目初始由Github上的某个项目clone而来，进行二次开发后，推送到公司仓库，通过持续集成进行部署。此时同时存在两个远程仓库：

1. Github上的仓库，依托开源框架，不断有新的特性加入，同时，整个项目的基础也在Github上某分支的某次提交
2. 公司代码仓库，存放实际上线的源码，与Github上的版本相比，进行了一些二次开发操作

希望达到的效果和可能存在的问题：

1. 本地项目的某个分支，可以自由的进行二次开发，并且支持定时拉取Github上的新代码，拉取新代码后，可以与公司代码合并后，推送的公司代码仓库
2. 保留github上的提交记录
3. 使用Git配置文件来同步git配置

## 可行的方案

1. 设定两个remote，假设github指向github仓库，prod指向本地生产仓库，则需要拉取Github更新并合并到本地生产仓库的dev分支时，主要步骤为
```git
---拉取生产仓库最新代码
git checkout master
git pull
--- 在最新代码的基础上新开一个dev分支
git checkout -b dev2019
--- 拉取github中master分支最新代码
git fetch github/master
--- 合并
git merge
git commit -m "合并Github代码到dev分支"
--- 切换到master，合并并推送
git checkout master
git merge dev2019
git commit -m "合并到主分支"
git push prod/master
```

## 实验过程

1. 准备三个仓库，模拟代码环境

```
略
