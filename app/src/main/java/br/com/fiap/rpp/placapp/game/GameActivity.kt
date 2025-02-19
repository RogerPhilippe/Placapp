package br.com.fiap.rpp.placapp.game

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.fiap.rpp.placapp.R
import br.com.fiap.rpp.placapp.awayteam.AwayTeamFragment
import br.com.fiap.rpp.placapp.event.EventFragment
import br.com.fiap.rpp.placapp.hometeam.HomeTeamFragment
import br.com.fiap.rpp.placapp.score.ScoreActivity
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    private var eventName = ""
    private var homeTeam = ""
    private var awayTeam = ""
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        this.initViewModel()
        this.showEventFragment()

        ivBack.setOnClickListener { onBackPressed() }
        this.registerBroadcastReceiver()
    }

    private fun initViewModel() {
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
    }

    private fun showEventFragment() {
        val ft = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentByTag("EventFragment") == null) {
            ft.add(R.id.containerGame, EventFragment(), "EventFragment")
            ft.commit()
        }
    }

    private fun registerBroadcastReceiver() {
        val intentFilter = IntentFilter("FILTER_EVENT")
        intentFilter.addAction("FILTER_HOME_TEAM")
        intentFilter.addAction("FILTER_AWAY_TEAM")
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, intentFilter)
    }
    public override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.hasExtra("event_name")) {
                gameViewModel.eventName = intent.getStringExtra("event_name")
                showHomeTeamFragment()
            }
            if (intent.hasExtra("home_team")) {
                gameViewModel.homeTeam = intent.getStringExtra("home_team")
                showAwayTeamFragment()
            }
            if (intent.hasExtra("away_team")) {
                gameViewModel.awayTeam = intent.getStringExtra("away_team")
                showScoreActivity()
            }
        }
    }

    private fun showHomeTeamFragment() {
        nextFragment(HomeTeamFragment())
    }
    private fun showAwayTeamFragment() {
        nextFragment(AwayTeamFragment())
    }
    private fun nextFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        ft.replace(R.id.containerGame, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }
    private fun showScoreActivity() {
        val nextScreen = Intent(this@GameActivity, ScoreActivity::class.java)
        nextScreen.putExtra("eventName", gameViewModel.eventName)
        nextScreen.putExtra("homeTeam", gameViewModel.homeTeam)
        nextScreen.putExtra("awayTeam", gameViewModel.awayTeam)
        startActivity(nextScreen)
        finish()
    }

}
