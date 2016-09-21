package com.waznop.paddlebear;

import com.badlogic.gdx.math.Vector2;
import com.waznop.gameworld.BearEnum;

/**
 * Created by Waznop on 2016-09-18.
 */
public class HelperFunctions {

    public static Vector2 rotateAtPoint(float x, float y, float pointX, float pointY, float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        x -= pointX;
        y -= pointY;

        float newX = x * cos - y * sin;
        float newY = x * sin + y * cos;

        return new Vector2(newX + pointX, newY + pointY);
    }

    public static Vector2 rotateAtPoint(Vector2 position, float pointX, float pointY, float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        position.x -= pointX;
        position.y -= pointY;

        float newX = position.x * cos - position.y * sin;
        float newY = position.x * sin + position.y * cos;

        position.x = newX + pointX;
        position.y = newY + pointY;

        return position;
    }

    public static BearEnum getBearEnumFromString(String name) {
        for (BearEnum bear : BearEnum.values()) {
            if (name.equals(bear.name)) {
                return bear;
            }
        }
        return Constants.DEFAULT_BEAR;
    }

}
