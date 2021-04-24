package com.thinvent.utils;

import com.thinvent.entity.DicItem;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * author yb
 * createTime 2020/10/19
 */
//树形结构工具类
public class TreeUtils {

    public Map<String,Object> mapArray = new LinkedHashMap<String, Object>();

    public List<DicItem> menuCommon;
    public List<Object> list = new ArrayList<>();

    public List<Object> treeMenu(List<DicItem> menu){
        menuCommon = menu;
        for (DicItem dicItem : menu) {
            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
            if (dicItem.getAreaLevel()==1) {
                setTreeMap(mapArr,dicItem);
                list.add(mapArr);
            }
        }
        return list;
    }

    public List<?> menuChild(String fid){
        if(!StringUtils.isEmpty(fid)) {
            List<Object> lists = new ArrayList<Object>();
            for (DicItem dicItem : menuCommon) {
                Map<String, Object> childArray = new LinkedHashMap<String, Object>();
                if (fid.equals(dicItem.getItemParent())) {
                    setTreeMap(childArray, dicItem);
                    lists.add(childArray);
                }

            }
            return lists;
        }else{
            return null;
        }
    }

    //写上你的类结构
    private void setTreeMap(Map<String,Object> mapArr,DicItem dicItem){
        mapArr.put("label",dicItem.getItemValue());
        mapArr.put("id", dicItem.getItemValue());
        mapArr.put("value", dicItem.getItemValue());
        if(dicItem.getAreaLevel()==1||dicItem.getAreaLevel()==2) {
            mapArr.put("children", menuChild(dicItem.getItemKey()));
        };
    }

}

