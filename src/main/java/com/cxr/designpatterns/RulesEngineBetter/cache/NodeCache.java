package com.cxr.designpatterns.RulesEngineBetter.cache;

import com.alibaba.fastjson.JSON;
import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseNode;
import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseRelationNode;
import com.cxr.designpatterns.RulesEngineBetter.leafNode.BaseFlowNode;
import com.cxr.designpatterns.RulesEngineBetter.leafNode.BaseNoneNode;
import com.cxr.designpatterns.RulesEngineBetter.leafNode.BaseResultNode;
import com.cxr.designpatterns.RulesEngineBetter.model.NodeDo;
import com.cxr.designpatterns.RulesEngineBetter.model.NodeTypeEnum;
import com.cxr.designpatterns.RulesEngineBetter.relationNode.All;
import com.cxr.designpatterns.RulesEngineBetter.relationNode.And;
import com.cxr.designpatterns.RulesEngineBetter.relationNode.Any;
import com.cxr.designpatterns.RulesEngineBetter.relationNode.None;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @Date 2022/5/13 11:43 上午
 * @Created by CiXingrui
 */
@Data
@Component
public class NodeCache {

    static List<NodeDo> dos = new ArrayList<>();

    static {

        NodeDo nodeDo1 = new NodeDo().setNodeId("1").setNodeConf(null).setNodeDesc("第一个根节点").setNodeRefrence(null).setNodeTypeEnum(NodeTypeEnum.NONE).setSonIds("2,3").setScenesId("发放优惠券");
        NodeDo nodeDo2 = new NodeDo().setNodeId("2").setNodeConf("{\"time\":\"2021-10-02 00:00:00\"}").setNodeDesc("修改时间").setNodeRefrence("com.cxr.designpatterns.RulesEngineBetter.useModel.TimeChangeNone").setNodeTypeEnum(NodeTypeEnum.LEAF_NONE).setSonIds("").setScenesId("发放优惠券");
        NodeDo nodeDo3 = new NodeDo().setNodeId("3").setNodeConf(null).setNodeDesc("Any节点").setNodeRefrence(null).setNodeTypeEnum(NodeTypeEnum.ANY).setSonIds("4,5").setScenesId("发放优惠券");
        NodeDo nodeDo4 = new NodeDo().setNodeId("4").setNodeConf(null).setNodeDesc("AND - 100元5余额").setNodeRefrence(null).setNodeTypeEnum(NodeTypeEnum.AND).setSonIds("6,7").setScenesId("发放优惠券");
        NodeDo nodeDo5 = new NodeDo().setNodeId("5").setNodeConf(null).setNodeDesc("AND - 50元10积分").setNodeRefrence(null).setNodeTypeEnum(NodeTypeEnum.AND).setSonIds("8,9").setScenesId("发放优惠券");
        NodeDo nodeDo6 = new NodeDo().setNodeId("6").setNodeConf("{\"score\":100,\"key\":\"cost\"}").setNodeDesc("满100元").setNodeRefrence("com.cxr.designpatterns.RulesEngineBetter.useModel.ScoreFlow").setNodeTypeEnum(NodeTypeEnum.LEAF_FLOW).setSonIds("").setScenesId("发放优惠券");
        NodeDo nodeDo7 = new NodeDo().setNodeId("7").setNodeConf("{\"key\":\"uid\",\"value\":5}").setNodeDesc("账户发放10元").setNodeRefrence("com.cxr.designpatterns.RulesEngineBetter.useModel.AmountResult").setNodeTypeEnum(NodeTypeEnum.LEAF_RESULT).setSonIds("").setScenesId("发放优惠券");
        NodeDo nodeDo8 = new NodeDo().setNodeId("8").setNodeConf("{\"key\":\"cost\",\"score\":50}").setNodeDesc("满50元").setNodeRefrence("com.cxr.designpatterns.RulesEngineBetter.useModel.ScoreFlow").setNodeTypeEnum(NodeTypeEnum.LEAF_FLOW).setSonIds("").setScenesId("发放优惠券");
        NodeDo nodeDo9 = new NodeDo().setNodeId("9").setNodeConf("{\"key\":\"uid\",\"value\":10}").setNodeDesc("账户发5积分").setNodeRefrence("com.cxr.designpatterns.RulesEngineBetter.useModel.PointResult").setNodeTypeEnum(NodeTypeEnum.LEAF_RESULT).setSonIds("").setScenesId("发放优惠券");

        dos.add(nodeDo1);
        dos.add(nodeDo2);
        dos.add(nodeDo3);
        dos.add(nodeDo4);
        dos.add(nodeDo5);
        dos.add(nodeDo6);
        dos.add(nodeDo7);
        dos.add(nodeDo8);
        dos.add(nodeDo9);
    }

    /**
     * 就是下面那个缓存 根据场景id区分下
     * 这个缓存支持多个场景
     */
    private static Map<String, Map<String, BaseNode>> sceneCacheMap = new HashMap();


    /**
     * 通过节点id获取节点信息缓存map
     * <p>
     * key:nodeId
     * value:节点信息 + children 【只有关系节点才有children信息，因为getChildren()是抽象的relationNode才有的方法】
     */
    public static Map<String, BaseNode> cache = new HashMap<>();

    @PostConstruct
    void init() throws ClassNotFoundException {
        getNodeCache(dos);
    }

    /**
     * 比如现在我通过场景 "打折" 查询db查到一个list的db数据
     * 然后调用下面的接口，返回包括打折场景在内的所有节点信息
     */
    public static Map<String, BaseNode> getNodeCache(List<NodeDo> dos) throws ClassNotFoundException {

        List<String> errors = new ArrayList<>(dos.size());
        //当前这些节点的缓存map
        Map<String, BaseNode> dosCacheMap = new HashMap<>(dos.size());

        //先把节点实例化，还没填充内容
        for (NodeDo aDo : dos) {
            dosCacheMap.put(aDo.getNodeId(), convert(aDo));
        }

        for (NodeDo aDo : dos) {
            //拿到节点信息
            BaseNode baseNode = dosCacheMap.get(aDo.getNodeId());

            //【关系节点】填充children信息
            if (NodeTypeEnum.isRelation(aDo.getNodeTypeEnum().getType())) {
                //获取子节点列表
                List<String> sonlist = getSonList(aDo.getSonIds());

                for (String son : sonlist) {
                    //子节点信息
                    BaseNode sonNode = dosCacheMap.get(son);
                    //当前节点信息 必然是关系节点 - 这里强转的时候会 new BaseRelationNode()
                    BaseRelationNode baseRelationNode = (BaseRelationNode) dosCacheMap.get(aDo.getNodeId());
                    //对当前关系节点填充children信息
                    baseRelationNode.getChildren().add(sonNode);

                }

            }

        }

        cache.putAll(dosCacheMap);
        return cache;
    }

    private static List<String> getSonList(String sonIds) {
        if (StringUtils.isEmpty(sonIds)) {
            return new ArrayList<>();
        }

        List<String> strings = new ArrayList<>();
        Arrays.stream(sonIds.split(",")).forEach(m -> strings.add(m));
        return strings;
    }

    /**
     * 根据do创建baseNode
     * db中存的是一行一行的节点数据，我们现在要把这行数据变成实例化的node节点
     * 用baseNode接收 - 多态
     */
    private static BaseNode convert(NodeDo aDo) throws ClassNotFoundException {

        BaseNode baseNode;

        switch (aDo.getNodeTypeEnum()) {

            case LEAF_FLOW:
                /**
                 * {"score":100,"key":"cost"}
                 * 这里被实例化成我们自己写的类了 所以我们自己写的类必须有
                 * score key这两个字段
                 */
                baseNode = (BaseFlowNode) JSON.parseObject(aDo.getNodeConf(), Class.forName(aDo.getNodeRefrence()));
                baseNode.setNodeName(aDo.getClass().getSimpleName());
                baseNode.setNodeId(aDo.getNodeId());
                break;

            case LEAF_RESULT:
                baseNode = (BaseResultNode) JSON.parseObject(aDo.getNodeConf(), Class.forName(aDo.getNodeRefrence()));
                baseNode.setNodeName(aDo.getClass().getSimpleName());
                baseNode.setNodeId(aDo.getNodeId());
                break;

            case LEAF_NONE:
                baseNode = (BaseNoneNode) JSON.parseObject(aDo.getNodeConf(), Class.forName(aDo.getNodeRefrence()));
                baseNode.setNodeName(aDo.getClass().getSimpleName());
                baseNode.setNodeId(aDo.getNodeId());
                break;

            case NONE:
                //这里就不用再用我们配置的url实例化了，直接实例化成我们写好的节点就行了
                String confNONE = Objects.isNull(aDo.getNodeConf()) ? "{}" : aDo.getNodeConf();
                baseNode = JSON.parseObject(confNONE, None.class);
                baseNode.setNodeName("None");
                baseNode.setNodeId(aDo.getNodeId());
                break;

            case AND:
                String confAnd = Objects.isNull(aDo.getNodeConf()) ? "{}" : aDo.getNodeConf();

                baseNode = JSON.parseObject(confAnd, And.class);
                baseNode.setNodeName("And");
                baseNode.setNodeId(aDo.getNodeId());
                break;

            case ALL:
                String confALL = Objects.isNull(aDo.getNodeConf()) ? "{}" : aDo.getNodeConf();

                baseNode = JSON.parseObject(confALL, All.class);
                baseNode.setNodeName("All");
                baseNode.setNodeId(aDo.getNodeId());
                break;

            case ANY:
                String confANY = Objects.isNull(aDo.getNodeConf()) ? "{}" : aDo.getNodeConf();

                baseNode = JSON.parseObject(confANY, Any.class);
                baseNode.setNodeName("Any");
                baseNode.setNodeId(aDo.getNodeId());
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + aDo.getNodeTypeEnum());
        }

        return baseNode;
    }


}
