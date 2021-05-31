package com.cxr.other.utilsSelf.treeDto;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SupermanService {


    /**
     * 查询所有赛亚人
     */
    public List<Superman> getAllSuperman() {
        // 用来存储最终的结果
        List<Superman> list = new ArrayList<>();

        // 源数据，需要处理
        List<Superman> data = new ArrayList<>();

        /**
         * 这个map的就是 <pid,JavaBean>
         */
        HashMap<Integer, Superman> hashMap = new HashMap<>();
        for (Superman superman : data) {
            hashMap.put(superman.getId(), superman);
        }

        // 遍历源数据
        for (Superman child : data) {
            if(child.getPid() == 0){
                /**
                 * 找到根节点并存储
                 * 其实这个list至始至终可能只存储一棵树（也就是只有一个根节点（Pid==0))
                 * 要是想往list里面存多个 那就同级的呗 多颗树
                 */
                list.add(child);
            } else {
                // 如果不是根节点，则找到上一级，并把自己设置为上一级的子节点
                // map里面存的已经是全部数据了，我们遍历它，然后让所有的数据找到自己的爸爸 也就是把被遍历的数据设置为爸爸的儿子
                Superman parent = hashMap.get(child.getPid());
                parent.getChildren().add(child);
            }
        }

        return list;
    }

}
