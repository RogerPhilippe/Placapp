package br.com.fiap.rpp.placapp.awayteam


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.fiap.rpp.placapp.R
import br.com.fiap.rpp.placapp.extensions.setupClearButtonWithAction
import br.com.fiap.rpp.placapp.score.ScoreActivity
import kotlinx.android.synthetic.main.fragment_away_team.*
import kotlinx.android.synthetic.main.fragment_event.btNextStep

class AwayTeamFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_away_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputAwayTeam.setupClearButtonWithAction()

        btNextStep.setOnClickListener { this.nextScreen() }
    }

    private fun nextScreen() {

        this.sendAwayTeamName()
        startActivity(Intent(activity, ScoreActivity::class.java))
        activity?.finish()
    }

    private fun sendAwayTeamName() {
        val intent = Intent("FILTER_AWAY_TEAM")
        intent.putExtra("away_team", inputAwayTeam.text.toString())
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }

}
