/**父层*/
var parent = {};
/**子层*/
var children = {};


/**
 * 只能选父层，选中父层之后，再选子层，则子层的数据都不会记录到缓存中
 *
 * 如果没有选中父层，则可以选子层，但是选完子层又选了父层，则只有父层有效，子层无效
 *
 * @param treeNode zTree的节点
 */
function sortOnlyByParent(treeNode) {
    let now = treeNode.tId;
    let parentId = treeNode.getParentNode().tId;
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
            if (!parent.hasOwnProperty(now)) {
                parent[now] = nowPuid;
                /**
                 * 子缓存中有父缓存，删除父缓存
                 */
                if (children.hasOwnProperty(now)) {
                    delete children[now];
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
                }
                else if (!children[parentId].hasOwnProperty(now)) {
                    children[parentId][now] = nowPuid;
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
            delete parent[now];
            delete children[now];
        }
        /**
         * 子层不选中，删除子中的子缓存
         */
        else if (!treeNode.isParent && children.hasOwnProperty(parentId) && children[parentId].hasOwnProperty(now)) {
            delete children[parentId][now];
            if ($.isEmptyObject(children[parentId])) {
                delete children[parentId];
            }
        }
    }
}