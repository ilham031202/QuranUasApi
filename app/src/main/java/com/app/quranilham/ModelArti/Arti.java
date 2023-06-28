package com.app.quranilham.ModelArti;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Arti{

	@SerializedName("translations")
	private List<TranslationsItem> translations;

	@SerializedName("meta")
	private Meta meta;

	public List<TranslationsItem> getTranslations(){
		return translations;
	}

	public Meta getMeta(){
		return meta;
	}

	@Override
 	public String toString(){
		return 
			"Arti{" + 
			"translations = '" + translations + '\'' + 
			",meta = '" + meta + '\'' + 
			"}";
		}
}