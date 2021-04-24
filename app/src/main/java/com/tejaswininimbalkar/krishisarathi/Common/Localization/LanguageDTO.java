package com.tejaswininimbalkar.krishisarathi.Common.Localization;

/*
 * @author Tejaswini Nimbalkar
 */

public class LanguageDTO {
    private String languageCode, languageTitle;

    public LanguageDTO(String languageCode, String languageTitle) {
        this.languageCode = languageCode;
        this.languageTitle = languageTitle;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getLanguageTitle() {
        return languageTitle;
    }
}
