package app.lisboa.lisboapp.ui;

import app.lisboa.lisboapp.R;

/**
 * Created by Rajat Anantharam on 04/11/16.
 */
public class EmojiMapper {

    static int getImageResource(String emojiName) {

        if(emojiName == null || emojiName.length() == 0) {
            return R.drawable.fun;
        }

        switch (emojiName) {
            case "pool":
                return R.drawable.pool;
            case "pasta":
                return R.drawable.pasta;
            case "glasses":
                return R.drawable.glasses;
            case "beer":
                return R.drawable.beer;
            case "snack":
                return R.drawable.snack;
            case "sushi":
                return R.drawable.sushi;
            case "wine":
                return R.drawable.wine;
            case "breakfast":
                return R.drawable.breakfast;
            case "cocktail":
                return R.drawable.cocktail;
            case "coffee":
                return R.drawable.coffee;
            case "fun":
                return R.drawable.fun;
            case "heart":
                return R.drawable.heart;
            case "adventure":
                return R.drawable.adventure;
            default:
                return R.drawable.heart;
        }
    }
}
