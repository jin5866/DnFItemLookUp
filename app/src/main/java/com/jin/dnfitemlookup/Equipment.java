package com.jin.dnfitemlookup;

/**
 * Created by SSA on 2017-12-18.
 */

public class Equipment {
    String slotId; //부위 이름
    String itemId; //아이템 아이디
    String itemName; //아이템 이름
    String itemRarity; //레어도
    String setItemName;
    int reinforce; //강화,증폭
    int refine; //제련
    String amplificationName; //차원의 스텟

    private int icon;



    public String getItemName(){
        return "+" + reinforce + "  " + itemName + "\n";
    }

    public  String getItemInfo(){
        return "" + itemRarity + "  " + (setItemName == "null" ? "" :setItemName);
    }

    public Equipment(String slotId,String itemId,String itemName,String itemRarity,String setItemName,int reinforce,int refine,String amplificationName)
    {
        this.slotId = slotId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemRarity = itemRarity;
        this.setItemName = setItemName;
        this.reinforce = reinforce;
        this.refine = refine;
        this.amplificationName = amplificationName;

        setIcon();

    }

    public int getIcon(){
        return icon;
    }

    private void setIcon(){
        //아이콘 설정 --추가예정
        icon = R.drawable.ic_account_circle;
    }
}
