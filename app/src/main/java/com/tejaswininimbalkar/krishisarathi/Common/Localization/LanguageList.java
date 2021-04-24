package com.tejaswininimbalkar.krishisarathi.Common.Localization;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Tejaswini Nimbalkar
 */

public class LanguageList {

    public static List<LanguageDTO> getLanguageList() {
        List<LanguageDTO> languageList = new ArrayList<>();
        languageList.add(new LanguageDTO("en", "English"));
        languageList.add(new LanguageDTO("hi", "हिंदी"));
        languageList.add(new LanguageDTO("mr", "मराठी"));
        return languageList;
    }
}
