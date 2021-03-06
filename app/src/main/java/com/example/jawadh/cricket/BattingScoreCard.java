package com.example.jawadh.cricket;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jawadh.cricket.Other.Controller.ClubController;
import com.example.jawadh.cricket.Other.Controller.PlayerController;
import com.example.jawadh.cricket.Other.Model.Match;
import com.example.jawadh.cricket.Other.Model.Player;
import com.example.jawadh.cricket.Other.Model.User;

import java.io.IOException;
import java.net.URISyntaxException;

public class BattingScoreCard extends ActionBarActivity {


    private PlayerController playerController = PlayerController.getInstance();
    private ClubController clubController = ClubController.getInstance();
    private User user = CricManagerApp.getCurrentUser();
    private TextView textR1,textB1,text41,text61,textSR1,textR2,textB2,text42,text62,textSR2,textTotR,textWkts,textOvrs,textExtra ;
    private TextView player1;
    private TextView player2;
    private TextView verses;
    private int runs1=0,balls1=0,fours1 = 0,sixes1=0,runs2=0,balls2=0,fours2 = 0,sixes2=0,
            totalRuns=0,wickets=0,extras,balls=0,fours=0,sixes = 0;
    private double sr = 0.0,sr1 = 0.0,sr2 = 0.0;
    private String run1 = 0+"",ball1 = 0+"",four1=0+"",six1=0+"",SR1=0.0+"",run2 = 0+"",ball2 = 0+"",four2=0+"",
            six2=0+"",SR2=0.0+"",totalRun=0+"",wicket=0+"",ovr=0+"",extra=0+"",ball=0+"";
    private Button b0,b1,b2,b3,b4,b6,bWd,bNb,bLb,bB,bW,bEnd ; // here I have declared the score buttons
    private String MatchVerses="";
    private int player_id1 = 0;
    private int player_id2 = 0;
    private String playerName1,playerName2;
    private Player[] player = new Player[2];
    private Match match = CricManagerApp.getCurrentMatch();;
    private Player batsman1 = CricManagerApp.getCurrentBatsMan1();
    private Player batsman2 = CricManagerApp.getCurrentBatsMan2();
    public static int flagPlayer1 = 0;
    public static int flagPlayer2 = 0;

    @Override
    protected void onResume() {
        super.onResume();
        player1 = (TextView) findViewById(R.id.Player1);
        player2 = (TextView) findViewById(R.id.Player2);
        if(flagPlayer1==2) {
            batsman1 = CricManagerApp.getCurrentBatsMan1();
            playerName1 = batsman1.getName();
            batsman1.setOnStrike(true);
            Log.d("fffffffffgggggggggggg", batsman1.getName());
            Log.d("fffffffffgggggggggggg", flagPlayer1 + "");
            player[0] = batsman1;
            player1.setText(playerName1);
            setInitValuesP1();
            flagPlayer1 = 3;
        }
        if(flagPlayer2==2) {
            batsman2 = CricManagerApp.getCurrentBatsMan2();
            playerName2 = batsman2.getName();
            batsman2.setOnStrike(false);
            Log.d("fffffffffgggggggggggg", batsman2.getName());
            Log.d("fffffffffgggggggggggg",flagPlayer2+"");
            player[1] = batsman2;
            setInitValuesP2();
            player2.setText(playerName2);
            flagPlayer2 = 3;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batting_score_card);
        Bundle extras=getIntent().getExtras();
        player1 = (TextView) findViewById(R.id.Player1);
        player2 = (TextView) findViewById(R.id.Player2);

        MatchVerses = match.getVerses();


        verses = (TextView) findViewById(R.id.oclubname);

        verses.setText(MatchVerses);
        if(flagPlayer1==2) {
            playerName1 = batsman1.getName();
            batsman1.setOnStrike(true);
            Log.d("fffffffffgggggggggggg", batsman1.getName());
            Log.d("fffffffffgggggggggggg", flagPlayer1 + "");
            player[0] = batsman1;
        }
        if(flagPlayer2==2) {
            playerName2 = batsman2.getName();
            batsman2.setOnStrike(false);
            Log.d("fffffffffgggggggggggg", batsman2.getName());
            Log.d("fffffffffgggggggggggg",flagPlayer2+"");
            player[1] = batsman2;
        }
        if(flagPlayer1 == 0 || flagPlayer2==0){
            playerName1 = "Click me";
            playerName2 = "Click me";
            player[0] = new Player();
            player[1] = new Player();
        }
        player1.setText(playerName1);
        player2.setText(playerName2);
        setViews();
        setInitValues();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_card, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setViews(){
        textR1 = (TextView)findViewById(R.id.runsP1);
        textB1 = (TextView)findViewById(R.id.ballsP1);
        text41 = (TextView)findViewById(R.id.foursP1);
        text61 = (TextView)findViewById(R.id.sixesP1);
        textSR1 = (TextView)findViewById(R.id.SRP1);
        textR2 = (TextView)findViewById(R.id.runsP2);
        textB2 = (TextView)findViewById(R.id.ballsP2);
        text42 = (TextView)findViewById(R.id.foursP2);
        text62 = (TextView)findViewById(R.id.sixesP2);
        textSR2 = (TextView)findViewById(R.id.SRP2);
        textTotR= (TextView)findViewById(R.id.tscore);
        textWkts = (TextView) findViewById(R.id.twkts);
        textOvrs = (TextView)findViewById(R.id.tovrs);
        textExtra = (TextView)findViewById(R.id.textra);


    }
    public void setInitValuesP2(){
        textR2 .setText(match.getBatsMan2().getRun()+"");
        textB2 .setText(match.getBatsMan2().getBalls()+"");
        text42 .setText(match.getBatsMan2().getFours()+"");
        text62 .setText(match.getBatsMan2().getSixes()+"");
        textSR2 .setText(match.getBatsMan2().getsRate()+"");
    }
    public void setInitValues(){


        textTotR.setText(match.getScore()+"");
        textWkts .setText(match.getWickets()+"");
        textOvrs .setText(match.getOvers()+"");
        textExtra .setText(match.getExtras()+"");

    }
    public void setInitValuesP1(){

        textR1.setText(match.getBatsMan1().getRun()+"");
        textB1.setText(match.getBatsMan1().getBalls()+"");
        text41.setText(match.getBatsMan1().getFours()+"");
        text61.setText(match.getBatsMan1().getSixes() + "");
        textSR1 .setText(match.getBatsMan1().getsRate()+"");
    }
    public void updateText(View view) throws IOException, URISyntaxException {
        setViews();
        b0 = (Button)findViewById(R.id.button0);
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        b3 = (Button)findViewById(R.id.button3);
        b4 = (Button)findViewById(R.id.button4);
        b6 = (Button)findViewById(R.id.button6);
        bWd = (Button)findViewById(R.id.wide);
        bNb = (Button)findViewById(R.id.noball);
        bLb = (Button)findViewById(R.id.legbye);
        bB = (Button)findViewById(R.id.bye);
        bW = (Button)findViewById(R.id.out);
        bEnd = (Button)findViewById(R.id.endmatch);

        player[0].setRun(runs1);
        player[0].setSixes(sixes1);
        player[0].setFours(fours1);
        player[0].setsRate(sr1);
        player[0].setBalls(balls1);
        player[1].setRun(runs2);
        player[1].setSixes(sixes2);
        player[1].setFours(fours2);
        player[1].setsRate(sr2);
        player[1].setBalls(balls2);

        match.setVerses(MatchVerses);
        match.setScore(totalRuns);
        match.setWickets(wickets);
        match.setExtras(extras);
        match.setOvers(ovr);
        if(player[0].isOnStrike()){
            if(view.getId() == b0.getId()){
                balls1 +=1;
                sr1 = (runs1 * 100 / balls1);
            }
            if(view.getId() == b1.getId()){
                runs1 +=1;
                balls1 +=1;
                sr1 = (runs1 * 100 / balls1);
                player[0].setOnStrike(false);
                player[1].setOnStrike(true);
            }
            if(view.getId() == b2.getId()){
                runs1 +=2;
                balls1 +=1;
                sr1 = (runs1 * 100 / balls1);
            }
            if(view.getId() == b3.getId()){
                runs1 +=3;
                balls1 +=1;
                sr1 = (runs1 * 100 / balls1);
                player[0].setOnStrike(true);
                player[1].setOnStrike(false);
            }
            if(view.getId() == b4.getId()){
                runs1 += 4;
                balls1 += 1;
                fours1 += 1;
                sr1 = (runs1 * 100 / balls1);
            }
            if(view.getId() == b6.getId()){
                runs1 +=6;
                balls1 +=1;
                sixes1 += 1;
                sr1 = (runs1 * 100 / balls1);
            }
            if(view.getId() == bWd.getId()){
                totalRuns +=1;
                extras +=1;
            }
            if(view.getId() == bNb.getId()){
                totalRuns +=1;
                extras +=1;
            }
            if(view.getId() == bLb.getId()){
                totalRuns += 1;
                balls1 += 1;
                extras += 1;
            }
            if(view.getId() == bB.getId()){
                totalRuns+=1;
                balls1 +=1;
                extras +=1;
            }
            if(view.getId() == bW.getId()){
                balls1 +=1;
                wickets+=1;
                flagPlayer1=0;
                getPlayer1(view);
                // thread must be started to end data to the database
                AsyncTask at = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] params) {
                        try {
                            playerController.updateplayerScore(player[0],match.getVerses());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                };
                at.execute();
            }

        }
        // player 2 details will be updated.
        else if(player[1].isOnStrike()){
            if(view.getId() == b0.getId()){
                balls2 +=1;
                sr2 = (runs2 * 100 / balls2);
            }
            if(view.getId() == b1.getId()){
                runs2 +=1;
                balls2 +=1;
                sr2 = (runs2 * 100 / balls2);
                player[0].setOnStrike(true);
                player[1].setOnStrike(false);
            }
            if(view.getId() == b2.getId()){
                runs2 +=2;
                balls2 +=1;
                sr2 = (runs2 * 100 / balls2);
            }
            if(view.getId() == b3.getId()){
                runs2 +=3;
                balls2 +=1;
                sr2 = (runs2 * 100 / balls2);
                player[0].setOnStrike(true);
                player[1].setOnStrike(false);
            }
            if(view.getId() == b4.getId()){
                runs2 +=4;
                balls2 +=1;
                fours2 +=1;
                sr2 = (runs2 * 100 / balls2);
            }
            if(view.getId() == b6.getId()){
                runs2 +=6;
                balls2 +=1;
                sixes2 +=1;
                sr2 = (runs2 * 100 / balls2);
            }
            if(view.getId() == bWd.getId()){
                totalRuns +=1;
                balls2 +=1;
                extras +=1;
            }
            if(view.getId() == bNb.getId()){
                runs1 += 0;
                totalRuns += 1;
                balls2 += 1;
                extras +=1;
            }
            if(view.getId() == bLb.getId()){
                totalRuns+= 1;
                balls2 += 1;
                extras +=1;
            }
            if(view.getId() == bB.getId()){
                totalRuns+= 1;
                balls2 += 1;
                extras +=1;
            }
            if(view.getId() == bW.getId()){
                balls2 += 1;
                wickets+= 1;
                flagPlayer2 = 0;
                getPlayer2(view);
                // thread must be started to end data to the database
                AsyncTask at = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] params) {
                        try {
                            playerController.updateplayerScore(player[1],match.getVerses());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                };
                at.execute();
            }

        }
        run1 = runs1+"";
        run2 = runs2+"";

        totalRuns = runs1 + runs2;
        balls = balls1 + balls2;
        totalRun = totalRuns+"";
        wicket = wickets+"";
        ball = balls+"";
        ball1 = balls1+"";
        ball2 = balls2+"";
        extra = extras+"";
        four1 = fours1 + "";
        four2 = fours2 + "";
        six1 = sixes1+"";
        six2 = sixes2+"";
        SR1 = sr1+"";
        SR2 = sr2+"";
        ovr = (balls/6) + "." + (balls%6);
        textR1.setText(run1);
        textR2.setText(run2);
        textB1.setText(ball1);
        textB2.setText(ball2);
        text41.setText(four1);
        text42.setText(four2);
        text61.setText(six1);
        text62.setText(six2);
        textSR1.setText(SR1);
        textSR2.setText(SR2);
        textTotR.setText(totalRun);
        textWkts.setText(wicket);
        textOvrs.setText(ovr);
        textExtra.setText(extra);

        if(textOvrs.getText().equals(match.getMaxOvers()) || textWkts.getText().equals("10")){
            Toast.makeText(this,"Please touch the End of Match ",Toast.LENGTH_SHORT);
        }

    }

    public void setMatchScores(final View view) throws IOException, URISyntaxException {

        final int userId = user.getUserid();
        if(wickets == 10 || match.getOvers() == match.getMaxOvers()+"") {
            match.setVerses(MatchVerses);
            match.setScore(totalRuns);
            match.setWickets(wickets);
            match.setExtras(extras);
            match.setOvers(ovr);
        }
        AsyncTask at = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    clubController.updateMatchScore(match, userId);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if(o==null){
                    gotoMaenu(view);
                }
            }
        };
        at.execute();
    }
    public void getPlayer1(View view){
        Intent intent = new Intent(this,PlayerList.class);
        flagPlayer1 = 1;
        startActivity(intent);
    }
    public void getPlayer2(View view){
        Intent intent = new Intent(this,PlayerList.class);
        flagPlayer2 = 1;
        startActivity(intent);
    }
    public void gotoMaenu(View view){
        startActivity(new Intent(this,MatchScore.class));
    }
}

