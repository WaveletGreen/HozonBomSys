/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in  2018/11/22 11:04
 * @Modified By:
 */
/**父层*/
var parent = {};
/**子层*/
var children = {};
/**
 * 父层节点
 */
var parentNode = {};

/**
 * 子层节点
 */
var childrenNode = {};

/**
 * 只能选父层，选中父层之后，再选子层，则子层的数据都不会记录到缓存中
 *
 * 如果没有选中父层，则可以选子层，但是选完子层又选了父层，则只有父层有效，子层无效
 *
 * @param treeNode zTree的节点
 */
function sortOnlyByParent(treeNode, treeId) {
    let now = treeNode.tId;
    let parentId = "#";
    if (treeNode.getParentNode() != null) {
        parentId = treeNode.getParentNode().tId;
    }
    else {
        return -1;
    }
    var ztreeObj = $.fn.zTree.getZTreeObj(treeId);

    let nowPuid = treeNode.puid;
    let parentPuid = treeNode.puid;
    /**
     * 被选中，加入缓存
     */
    if (treeNode.CHECKED) {
        /**
         * 父层进入父缓存
         */
        if (treeNode.isParent) {
            let cNodes = treeNode.children;
            for (let i in cNodes) {
                if (cNodes[i].children != null && cNodes[i].children.length > 0) {
                    loopChildren(cNodes[i], ztreeObj, true, true);
                }
                // ztreeObj.checkNode(cNodes[i], true, false, false);
                cNodes[i].chkDisabled = true;
                // cNodes[i].checked = true;
                // cNodes[i].CHECKED = true;
                ztreeObj.updateNode(cNodes[i]);
            }
            if (!parent.hasOwnProperty(now)) {
                parent[now] = nowPuid;
                /**
                 * 加入节点
                 */
                parentNode[now] = treeNode;
                /**
                 * 子缓存中有父缓存，删除父缓存
                 */
                if (children.hasOwnProperty(now)) {
                    delete children[now];
                    /**
                     * 删除子
                     */
                    delete childrenNode[now];
                }
            }
        }
        /**
         * 子进入缓存
         */
        else {
            /**
             * 必须是父缓存没有选中才能进来，避免子缓存和父缓存冲突
             */
            if (!treeNode.getParentNode().CHECKED) {
                if (undefined == children[parentId]) {
                    let yy = {};
                    yy[now] = nowPuid;
                    children[parentId] = {};
                    children[parentId][now] = nowPuid;
                    /**
                     * 子节点
                     */
                    childrenNode[parentId] = {};
                    childrenNode[parentId][now] = treeNode;
                }
                else if (!children[parentId].hasOwnProperty(now)) {
                    children[parentId][now] = nowPuid;

                    /**
                     * 加入子节点
                     */
                    childrenNode[parentId][now] = treeNode;
                }
            }
        }
    }
    /**
     * 去除缓存中的内容
     */
    else {
        /**
         * 父层补选中，删除父缓存，删除子中的父缓存
         */
        if (treeNode.isParent && parent.hasOwnProperty(now)) {
            let cNodes = treeNode.children;
            for (var i in cNodes) {
                loopChildren(cNodes[i], ztreeObj, false, false);
                cNodes[i].chkDisabled = false;
                ztreeObj.updateNode(cNodes[i]);
                // ztreeObj.checkNode(cNodes[i], false, false, false);
                cNodes[i].CHECKED = false;
                // // cNodes[i].checked = false;
                ztreeObj.updateNode(cNodes[i]);
            }
            delete parent[now];
            delete children[now];
            /**
             * 删除
             */
            delete parentNode[now];
            delete childrenNode[now];
        }
        /**
         * 子层不选中，删除子中的子缓存
         */
        else if (!treeNode.isParent && children.hasOwnProperty(parentId) && children[parentId].hasOwnProperty(now)) {
            delete children[parentId][now];
            /**
             * 删除子节点
             */
            delete childrenNode[parentId][now];

            if ($.isEmptyObject(children[parentId])) {
                delete children[parentId];
                /**
                 * 节点中的父节点一起删除
                 */
                delete childrenNode[parentId];
            }
        }
    }
}

/**
 * 递归设置子节点的状态
 * @param childNode 有子节点的父节点
 * @param ztreeObj zTree对象
 * @param isCheck 是否是选中和是否禁用
 */
function loopChildren(childNode, ztreeObj, isCheck, isChkDisabled) {
    if (childNode.children != null && childNode.children.length > 0) {
        let cNodes = childNode.children;
        for (let i in cNodes) {
            if (cNodes[i].children != null && cNodes[i].children.length > 0) {
                loopChildren(cNodes[i], ztreeObj, isCheck, isChkDisabled);
                if (isCheck) {
                    //从父节点中移除所有该父节点下的子节点
                    if (parent.hasOwnProperty(cNodes[i].tId)) {
                        delete parent[cNodes[i].tId];
                        /**
                         * 父节点一起删除
                         */
                        delete parentNode[cNodes[i].tId];
                    }
                }
            }
            if (isCheck) {
                if (children.hasOwnProperty(cNodes[i].parentTId) && children[cNodes[i].parentTId].hasOwnProperty(cNodes[i].tId)) {
                    delete children[cNodes[i].parentTId];

                    /** */
                    delete childrenNode[cNodes[i].parentTId];
                }
            }
            cNodes[i].chkDisabled = isChkDisabled;
            ztreeObj.updateNode(cNodes[i]);
            ztreeObj.checkNode(cNodes[i], isCheck, false, false);
            ztreeObj.updateNode(cNodes[i]);
            let p = cNodes[i].getParentNode();
            if (p != null && !p.CHECKED) {
                if (parent.hasOwnProperty(p.tId)) {
                    delete parent[p.tId];
                    /** */
                    delete parentNode[p.tId];
                }
            }


        }
    }
    childNode.chkDisabled = isChkDisabled;
    ztreeObj.updateNode(childNode);
    ztreeObj.checkNode(childNode, isCheck, false, false);
    ztreeObj.updateNode(childNode);
    let pp = childNode.getParentNode();
    if (pp != null && !pp.CHECKED) {
        if (parent.hasOwnProperty(pp.tId)) {
            delete parent[pp.tId];
            /** */
            delete parentNode[pp.tId];
        }
    }
    if (isCheck) {
        //从父节点中移除所有该父节点下的子节点
        if (children.hasOwnProperty(childNode.parentTId) && children[childNode.parentTId].hasOwnProperty(childNode.tId)) {
            delete children[childNode.parentTId];

            delete childNode[childNode.parentTId];
        }
        if (parent.hasOwnProperty(childNode.tId)) {
            delete parent[childNode.tId];

            delete parentNode[childNode.tId];
        }
    }


}

/**
 * 返回所有根节点集合
 * @param treeObj zTree树结构
 * @returns {*}
 */
function getRoots(treeObj) {
    //返回根节点集合
    return treeObj.getNodesByFilter(function (node) {
        return node.level == 0
    });
}

/**
 * 返回一个根节点
 * @param treeObj
 * @returns {*}
 */
function getRoot(treeObj) {
    return treeObj.getNodesByFilter(function (node) {
        return node.level == 0
    }, true);
}