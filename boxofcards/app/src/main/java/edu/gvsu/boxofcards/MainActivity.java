package edu.gvsu.boxofcards;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.net.Uri;
import android.content.Intent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private enum player_e {singlePlayer, multiPlayer}
    private enum game_e {goFish, other}
    player_e player = player_e.singlePlayer;
    game_e game = game_e.goFish;

    ArrayList<Player> players = new ArrayList<Player>();
    Game g;
    int cardID;
    boolean ptoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void backMainButtonPressed(View v) {
        setContentView(R.layout.activity_main);
    }

    public void backPlayButtonPressed(View v) {
        setContentView(R.layout.activity_play);
        updatePlayerView(player);
    }

    public void playButtonPressed(View v) {
        setContentView(R.layout.activity_play);
        updatePlayerView(player_e.singlePlayer);
    }

    public void settingsButtonPressed(View v) {
        setContentView(R.layout.activity_error);
    }

    public void leftPlayNextPressed(View v) {
        player = player_e.singlePlayer;
        updatePlayerView(player);
    }

    public void rightPlayNextPressed(View v) {
        player = player_e.multiPlayer;
        updatePlayerView(player);
    }

    public void leftGameNextPressed(View v) {
        game = game_e.goFish;
        updateGameView(game);

        Button b = findViewById(R.id.infoButton);
        b.setVisibility(View.VISIBLE);
    }

    public void rightGameNextPressed(View v) {
        game = game_e.other;
        updateGameView(game);

        Button b = findViewById(R.id.infoButton);
        b.setVisibility(View.INVISIBLE);
    }

    public void gameBackButtonPressed(View v) {
        LinearLayout l = findViewById(R.id.backPopup);
        l.setVisibility(View.VISIBLE);
    }

    public void gameMainMenuButtonPressed(View v) {
        LinearLayout l = findViewById(R.id.backPopup);
        l.setVisibility(View.INVISIBLE);
        setContentView(R.layout.activity_main);
    }

    public void gameNewGameButtonPressed(View v) {
        LinearLayout l = findViewById(R.id.backPopup);
        l.setVisibility(View.INVISIBLE);
        setContentView(R.layout.gofish);
        setupGoFish();
    }

    public void gameContinueButtonPressed(View v) {
        LinearLayout l = findViewById(R.id.backPopup);
        l.setVisibility(View.INVISIBLE);
    }

    public void playerTypePressed(View v) {

        setContentView(R.layout.activity_game);
        updateGameView(game_e.goFish);
        
        if(player == player_e.singlePlayer) {
            //game.set_multiplayer(false);
        } else {
            //game.set_multiplayer(true);
        }
    }

    public void gameTypePressed(View v) {
        if(game == game_e.goFish) {
            setContentView(R.layout.gofish);
            setupGoFish();
            //game.startGoFish();
        } else {
            setContentView(R.layout.activity_error);
        }
    }

    public void gameInfoPressed(View v) {
        if(game == game_e.goFish) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://en.wikipedia.org/wiki/Go_Fish")));
        }
    }

    private void updatePlayerView(player_e i) {
        ImageView img;

        if(i == player_e.singlePlayer) {
            img = findViewById(R.id.singlePlayer);
            img.setVisibility(View.VISIBLE);
            img = findViewById(R.id.multiPlayer);
            img.setVisibility(View.INVISIBLE);
        } else {
            img = findViewById(R.id.singlePlayer);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.multiPlayer);
            img.setVisibility(View.VISIBLE);
        }
    }

    private void updateGameView(game_e i) {
        ImageView img;

        if(i == game_e.goFish) {
            img = findViewById(R.id.goFish);
            img.setVisibility(View.VISIBLE);
            img = findViewById(R.id.singlePlayer);
            img.setVisibility(View.INVISIBLE);
        } else {
            img = findViewById(R.id.goFish);
            img.setVisibility(View.INVISIBLE);
            img = findViewById(R.id.singlePlayer);
            img.setVisibility(View.VISIBLE);
        }
    }

    private void setupGoFish() {
        /*
        Random r = new Random();
        int card, count = 0;
        int[] pHand = new int[5];
        int[] cHand = new int[5];

        pScore = 0;
        cScore = 0;

        TextView playerScore = findViewById(R.id.playerScore);
        TextView compScore = findViewById(R.id.compScore);
        playerScore.setText(Integer.toString(pScore));
        compScore.setText(Integer.toString(cScore));

        while(count < 5) {
            card = r.nextInt(52);
            pHand[count] = card;

            addCard(card, true);

            count++;
        }

        count = 0;

        while(count < 5) {
            card = r.nextInt(52);
            cHand[count] = card;

            addCard(card, false);

            count++;
        }
        */

        Card c;
        g = new Game("GOFISH");

        players.add(new Player("User"));
        players.add(new Player("Computer"));

        for(Player p: players) {
            for(int i = 0; i < 5; i++){
                g.deal(p);
            }

            p.sortHand();
        }

        for(int i = 0; i < 5; i++) {
            c = players.get(0).hand.get(i);
            addCard(c.getCardInt(), true);

            c = players.get(1).hand.get(i);
            addCard(c.getCardInt(), false);
        }
    }

    private void playCard() {
        int x;

        x = g.tickGoFish(players, cardID);

        if(x == 1) {

        } else {
            cardID = g.deal(players.get(0)).getCardInt();
            drawCardDeck(false);
        }
    }

    private void addCard(int card, boolean player) {
        LinearLayout.LayoutParams imgparams = new LinearLayout.LayoutParams(150,200);
        LinearLayout layout;

        if(player) {
            layout = findViewById(R.id.playerHand); // //layoutID is id of the linearLayout that defined in your main.xml file
        } else {
            layout = findViewById(R.id.computerHand); // //layoutID is id of the linearLayout that defined in your main.xml file
        }

        ImageButton img = new ImageButton(this);
        if(player) {
            setCardBackground(img, card);
            img.setOnClickListener(cardClicked);
        } else {
            img.setBackgroundResource(R.drawable.back);
        }
        img.setId(card);
        img.setLayoutParams(imgparams);
        layout.addView(img);
    }

    private int drawCardPlayer(boolean finished) {
        if (finished) {
            findViewById(cardID).setVisibility(View.GONE);
            addCard(cardID, !ptoc);
            return 1;
        } else {
            playerAnimation(findViewById(cardID));
        }

        return 0;
    }

    private void drawCardDeck(boolean finished) {
        if(finished == true) {
            //play_card(v.getID())
            int card;
            Random r = new Random();

            card = r.nextInt(52);
            if (card == cardID) card = r.nextInt(52);

            resetDeckAnimation(findViewById(R.id.deckl));
            addCard(card, true);
        } else {
            deckAnimation(findViewById(R.id.deckl));
        }
    }

    private void deckAnimation(View v) {
        ObjectAnimator a = ObjectAnimator.ofFloat(v, "translationY", 400f);
        ObjectAnimator b = ObjectAnimator.ofFloat(v, "rotation", 90f, 0f);

        v.setVisibility(View.VISIBLE);

        a.setDuration(2000);
        a.addListener(dl);
        b.setDuration(1000);

        a.start();
        b.start();
    }

    private void playerAnimation(View v) {
        ObjectAnimator a;

        if(ptoc) {
            a = ObjectAnimator.ofFloat(v, "translationY", -400f);
        } else {
            a = ObjectAnimator.ofFloat(v, "translationY", 400f);
        }

        v.setVisibility(View.VISIBLE);

        a.setDuration(2000);
        a.addListener(pl);

        a.start();
    }

    private void resetDeckAnimation(View v) {
        v.setX(findViewById(R.id.deck).getX());
        v.setY(findViewById(R.id.deck).getY());
        v.setRotation(findViewById(R.id.deck).getRotation());
    }

    Animator.AnimatorListener dl = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            //postFirstAnimation(): This function is called after the animation ends
            animation.removeListener(dl);
            drawCardDeck(true);
        }
    };

    Animator.AnimatorListener pl = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            //postFirstAnimation(): This function is called after the animation ends
            animation.removeListener(pl);
            drawCardPlayer(true);
        }
    };

    View.OnClickListener cardClicked = new View.OnClickListener() {

        public void onClick(View v) {
            cardID = v.getId();
            ptoc = true;

            playCard();
            //drawCardDeck(false);
            //setCardBackground((ImageButton)findViewById(v.getId()), card);
        }
    };

    private void setCardBackground(ImageButton i, int c) {
        int s = c / 13;
        int x = c % 13;
        switch(s) {
            case 0:
                switch(x) {
                    case 0:
                        i.setBackgroundResource(R.drawable.h1);
                        break;
                    case 1:
                        i.setBackgroundResource(R.drawable.h2);
                        break;
                    case 2:
                        i.setBackgroundResource(R.drawable.h3);
                        break;
                    case 3:
                        i.setBackgroundResource(R.drawable.h4);
                        break;
                    case 4:
                        i.setBackgroundResource(R.drawable.h5);
                        break;
                    case 5:
                        i.setBackgroundResource(R.drawable.h6);
                        break;
                    case 6:
                        i.setBackgroundResource(R.drawable.h7);
                        break;
                    case 7:
                        i.setBackgroundResource(R.drawable.h8);
                        break;
                    case 8:
                        i.setBackgroundResource(R.drawable.h9);
                        break;
                    case 9:
                        i.setBackgroundResource(R.drawable.h10);
                        break;
                    case 10:
                        i.setBackgroundResource(R.drawable.h11);
                        break;
                    case 11:
                        i.setBackgroundResource(R.drawable.h12);
                        break;
                    case 12:
                        i.setBackgroundResource(R.drawable.h13);
                        break;
                }
                break;
            case 1:
                switch(x) {
                    case 0:
                        i.setBackgroundResource(R.drawable.d1);
                        break;
                    case 1:
                        i.setBackgroundResource(R.drawable.d2);
                        break;
                    case 2:
                        i.setBackgroundResource(R.drawable.d3);
                        break;
                    case 3:
                        i.setBackgroundResource(R.drawable.d4);
                        break;
                    case 4:
                        i.setBackgroundResource(R.drawable.d5);
                        break;
                    case 5:
                        i.setBackgroundResource(R.drawable.d6);
                        break;
                    case 6:
                        i.setBackgroundResource(R.drawable.d7);
                        break;
                    case 7:
                        i.setBackgroundResource(R.drawable.d8);
                        break;
                    case 8:
                        i.setBackgroundResource(R.drawable.d9);
                        break;
                    case 9:
                        i.setBackgroundResource(R.drawable.d10);
                        break;
                    case 10:
                        i.setBackgroundResource(R.drawable.d11);
                        break;
                    case 11:
                        i.setBackgroundResource(R.drawable.d12);
                        break;
                    case 12:
                        i.setBackgroundResource(R.drawable.d13);
                        break;
                }
                break;
            case 2:
                switch(x) {
                    case 0:
                        i.setBackgroundResource(R.drawable.c1);
                        break;
                    case 1:
                        i.setBackgroundResource(R.drawable.c2);
                        break;
                    case 2:
                        i.setBackgroundResource(R.drawable.c3);
                        break;
                    case 3:
                        i.setBackgroundResource(R.drawable.c4);
                        break;
                    case 4:
                        i.setBackgroundResource(R.drawable.c5);
                        break;
                    case 5:
                        i.setBackgroundResource(R.drawable.c6);
                        break;
                    case 6:
                        i.setBackgroundResource(R.drawable.c7);
                        break;
                    case 7:
                        i.setBackgroundResource(R.drawable.c8);
                        break;
                    case 8:
                        i.setBackgroundResource(R.drawable.c9);
                        break;
                    case 9:
                        i.setBackgroundResource(R.drawable.c10);
                        break;
                    case 10:
                        i.setBackgroundResource(R.drawable.c11);
                        break;
                    case 11:
                        i.setBackgroundResource(R.drawable.c12);
                        break;
                    case 12:
                        i.setBackgroundResource(R.drawable.c13);
                        break;
                }
                break;
            case 3:
                switch(x) {
                    case 0:
                        i.setBackgroundResource(R.drawable.s1);
                        break;
                    case 1:
                        i.setBackgroundResource(R.drawable.s2);
                        break;
                    case 2:
                        i.setBackgroundResource(R.drawable.s3);
                        break;
                    case 3:
                        i.setBackgroundResource(R.drawable.s4);
                        break;
                    case 4:
                        i.setBackgroundResource(R.drawable.s5);
                        break;
                    case 5:
                        i.setBackgroundResource(R.drawable.s6);
                        break;
                    case 6:
                        i.setBackgroundResource(R.drawable.s7);
                        break;
                    case 7:
                        i.setBackgroundResource(R.drawable.s8);
                        break;
                    case 8:
                        i.setBackgroundResource(R.drawable.s9);
                        break;
                    case 9:
                        i.setBackgroundResource(R.drawable.s10);
                        break;
                    case 10:
                        i.setBackgroundResource(R.drawable.s11);
                        break;
                    case 11:
                        i.setBackgroundResource(R.drawable.s12);
                        break;
                    case 12:
                        i.setBackgroundResource(R.drawable.s13);
                        break;
                }
                break;
        }
    }

}
