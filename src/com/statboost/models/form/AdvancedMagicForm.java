package com.statboost.models.form;


import com.avaje.ebean.Model;

import java.util.List;

/**
 * Created by Jon on 3/21/14.
 */
public class AdvancedMagicForm extends Model
{
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getCmc() {
        return cmc;
    }

    public void setCmc(String cmc) {
        this.cmc = cmc;
    }

    public String getCmcModifier(){
        return cmcModifier;
    }

    public void setCmcModifier(String cmcModifier){
        this.cmcModifier = cmcModifier;
    }

    public String getRulesContain() {
        return rulesContain;
    }

    public void setRulesContain(String rulesContain) {
        this.rulesContain = rulesContain;
    }

    public String getSetID() {
        return setID;
    }

    public void setSetID(String setID) {
        this.setID = setID;
    }

    public String getOrderByGroup() {
        return orderByGroup;
    }

    public void setOrderByGroup(String orderByGroup) {
        this.orderByGroup = orderByGroup;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getRarities() {
        return rarities;
    }

    public void setRarities(List<String> rarities) {
        this.rarities = rarities;
    }

    private String cardName;
    private String type;
    private String subType;
    private String cmc;
    private String cmcModifier;
    private String rulesContain;
    private String setID;
    private String orderByGroup;
    private String order;
    private List<String> colors;

    private List<String> rarities;

    public AdvancedMagicForm()
    {

    }


}
