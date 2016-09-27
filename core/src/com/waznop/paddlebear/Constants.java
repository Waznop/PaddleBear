package com.waznop.paddlebear;

import com.waznop.gameworld.BearEnum;

/**
 * Created by Waznop on 2016-08-18.
 */
public class Constants {

    // relative dimensions
    public static float SCALED_SCREEN_WIDTH;
    public static float SCALED_SCREEN_HEIGHT;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static float GAME_SCALE;
    public static int GAME_START_X;
    public static int GAME_START_Y;
    public static int GAME_MID_X;
    public static int GAME_MID_Y;

    // fixed dimensions
    public final static int GAME_WIDTH = 135;
    public final static int GAME_HEIGHT = 240;
    public final static int GAME_LEFT_BOUND = 24;
    public final static int GAME_RIGHT_BOUND = 114;
    public final static float WH_RATIO = 0.5625f;

    // in game values
    public final static int BEAR_SIZE = 16;
    public final static int BOAT_RADIUS = 7;
    public final static int SHORT_LOG_WIDTH = 24;
    public final static int LONG_LOG_WIDTH = 36;
    public final static int LOG_HEIGHT = 9;
    public final static int BABY_CUB_SIZE = 12;
    public final static int SCORE_LINE_HEIGHT = 222;

    // physics
    public final static float RESISTANCE = 0.98f;
    public final static float LA_FORCE_A = 32;
    public final static float LA_FORCE_B = 40;
    public final static float LA_FORCE_C = 24;
    public final static float LA_BACK_FORCE_A = -16;
    public final static float LA_BACK_FORCE_B = -20;
    public final static float LA_BACK_FORCE_C = -12;
    public final static float AA_FORCE_A = 16;
    public final static float AA_FORCE_B = 20;
    public final static float AA_FORCE_C = 12;
    public final static float PADDLE_TIMER_A = 1;
    public final static float PADDLE_TIMER_B = 0.67f;
    public final static float PADDLE_TIMER_C = 0.33f;

    // scrolling
    public final static float BOAT_SCROLL_Y = 20;
    public final static float RIVER_SCROLL_Y = 25;
    public final static float LAND_SCROLL_Y = 15;
    public final static float FASTFORWARD_SCROLL_Y = 100;
    public final static float FASTFORWARD_START = 0.2f;
    public final static float FASTFORWARD_END = 0.8f;

    // spawning
    public final static float SPAWN_SCROLL_MIN_Y = 22;
    public final static float SPAWN_SCROLL_RANGE_Y = 6;
    public final static float SPAWN_TIME_START = 7;
    public final static float SPAWN_TIME_PROGRESSION = 0.1f;
    public final static float SPAWN_TIME_CAP = 3;
    public final static float SPAWN_CUB_CHANCE = 0.2f;
    public final static int SPAWN_MARGIN = 30;

    // debug
    public final static boolean IS_INVINCIBLE = false;
    public static boolean SHOW_DEBUG = false;

    // misc
    public final static int PARTICLE_POOL_SIZE = 16;
    public final static float END_GAME_TIME = 2;
    public final static BearEnum DEFAULT_BEAR = BearEnum.TEDDY;

    // UI
    public final static int PLAY_BUTTON_OFFSET_Y = -18;
    public final static int SHOP_BUTTON_OFFSET_Y = -6;
    public final static int RESTART_BUTTON_OFFSET_Y = 16;
    public final static int BACK_BUTTON_OFFSET_Y = 38;
    public final static int POST_MENU_OFFSET_Y = -72;
    public final static int POST_MENU_FIELD_OFFSET_X = -23;
    public final static int POST_MENU_STAT_OFFSET_X = 23;
    public final static int POST_MENU_SCORE_OFFSET_Y = -60;
    public final static int POST_MENU_BEST_OFFSET_Y = -44;
    public final static int POST_MENU_CUB_OFFSET_Y = -28;
    public final static int SHOP_BACK_BUTTON_OFFSET_Y = 12;
    public final static int SHOP_ICON_OFFSET = 6;
    public final static int SHOP_DETAILS_BUTTON_OFFSET_X = 3;
    public final static int SHOP_DETAILS_BUTTON_OFFSET_Y = 32;

    // shop
    public final static int SHOP_TEDDY_PRICE = 0;
    public final static int SHOP_SPADDULA_PRICE = 10;
    public final static int SHOP_JACUB_PRICE = 50;
    public final static int SHOP_TSUNDER_PRICE = 100;
    public final static int SHOP_WOLF_PRICE = 200;
    public final static int SHOP_TEDDY_OFFSET_Y = -60;
    public final static int SHOP_SPADDULA_OFFSET_Y = -25;
    public final static int SHOP_JACUB_OFFSET_Y = 10;
    public final static int SHOP_TSUNDER_OFFSET_Y = 45;
    public final static int SHOP_WOLF_OFFSET_Y = 80;
    public final static String TEDDY_NAME = "TEDDY";
    public final static String SPADDULA_NAME = "SPADDULA";
    public final static String JACUB_NAME = "JACUB";
    public final static String TSUNDER_NAME = "TSUNDER";
    public final static String WOLF_NAME = "TEDDY 2.0";

    // descriptions
    public final static String TEDDY_DESCRIPTION =
            "It's just Teddy. He's a monkey.";
    public final static String SPADDULA_DESCRIPTION =
            "Everyone asks her for a second cub.";
    public final static String JACUB_DESCRIPTION =
            "They say his armor is made of cubalt.";
    public final static String TSUNDER_DESCRIPTION =
            "It's not like she's a good conductor.";
    public final static String WOLF_DESCRIPTION =
            "Ay good job you almost beat the game!";
    public final static String CONFIRM_BUY_STRING =
            "Are you sure you can bear this bear?";
    public final static String ALREADY_OWN_STRING =
            "You already own this bear. No refunds.";

    // tutorial
    public final static String TUTORIAL_STRING_1 =
            "Gently paddle your way through thousands of bridges.";
    public final static String TUTORIAL_STRING_2 =
            "To do so, tap on one of the four corners of the screen.";
    public final static String TUTORIAL_STRING_3 =
            "They determine the side and direction of your paddling.";
    public final static String TUTORIAL_STRING_4 =
            "However -";
    public final static String TUTORIAL_STRING_5 =
            "Touch the lands and thou wilt sink.";
    public final static String TUTORIAL_STRING_6 =
            "Touch the logs and thou wilt still sink.";
    public final static String TUTORIAL_STRING_7 =
            "Go out of sight and thou wilt... sink?";
    public final static String TUTORIAL_STRING_8 =
            "Touch the cubs and thou shalt sink...";
    public final static String TUTORIAL_STRING_9 =
            "...thy currency in the shop later :D";

    // credits
    public final static String CREDITS_STRING_1 =
            "Credits:";
    public final static String CREDITS_STRING_2 =
            "Developer: Waznop";
    public final static String CREDITS_STRING_3 =
            "Artist: Waznop";
    public final static String CREDITS_STRING_4 =
            "Audio: Waznop";
    public final static String CREDITS_STRING_5 =
            "Waznop: Hong Tai Wei";
    public final static String CREDITS_STRING_6 =
            "Please send comments or bugs to:";
    public final static String CREDITS_STRING_7 =
            "waznop@gmail.com";
    public final static String CREDITS_STRING_8 =
            "Have a nice day you fools <3";
    public final static String CREDITS_STRING_9 =
            "ver.1.02 @UW 2016";

}
