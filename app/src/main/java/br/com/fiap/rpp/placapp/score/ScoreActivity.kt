package br.com.fiap.rpp.placapp.score

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.rpp.placapp.R
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : AppCompatActivity() {

    private lateinit var scoreViewModel: ScoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        this.initViewModel()
        this.setExtras()
        this.registerListener()
        this.registerObservers()
    }

    private fun initViewModel() {
        scoreViewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)
    }

    private fun setExtras() {
        tvEventName.text = intent.extras?.getString("eventName")
        tvHomeName.text = intent.extras?.getString("homeTeam")
        tvAwayName.text = intent.extras?.getString("awayTeam")
    }

    private fun registerObservers() {

        scoreViewModel.goalAway.observe(this, Observer { tvAwayGoal.text = it.toString() })
        scoreViewModel.goalHome.observe(this, Observer { tvGoalHome.text = it.toString() })
    }

    private fun registerListener() {
        btGoalHome.setOnClickListener { scoreViewModel.goalHome() }
        btAwayGoal.setOnClickListener { scoreViewModel.goalAway() }
        btRestart.setOnClickListener { scoreViewModel.restartGame() }
        btScoreExit.setOnClickListener { finish() }
    }

}
