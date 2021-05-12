package com.keyin.designpattern.structure7.adapter;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: yinke
 * @create: 2021-04-28 17:42
 **/
@Slf4j
public class Chinese2EnglishChargerAdapt implements EnglishCharge{
    private ChineseCharger chineseCharger;

    public Chinese2EnglishChargerAdapt(ChineseCharger chineseCharger) {
        this.chineseCharger = chineseCharger;
    }
    public Chinese2EnglishChargerAdapt() {
        this.chineseCharger = new ChineseCharger();
    }
    @Override
    public void englishCharge() {
        log.info("now charge follow english standard.");
        chineseCharger.chineseCharge();
    }
}
