package com.example.a79875.todaynews.enity;

public class BotBean {
    String iconName;// 导航栏图标名字

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getUnCheckedIcon() {
        return unCheckedIcon;
    }

    public void setUnCheckedIcon(int unCheckedIcon) {
        this.unCheckedIcon = unCheckedIcon;
    }

    int unCheckedIcon;// 未选中的图标
    public BotBean(String iconName, int unCheckedIcon){
        this.iconName = iconName;
        this.unCheckedIcon = unCheckedIcon;
    }


}
