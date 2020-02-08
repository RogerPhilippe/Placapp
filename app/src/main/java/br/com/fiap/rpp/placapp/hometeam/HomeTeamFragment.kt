package br.com.fiap.rpp.placapp.hometeam


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.fiap.rpp.placapp.R
import br.com.fiap.rpp.placapp.awayteam.AwayTeamFragment
import br.com.fiap.rpp.placapp.extensions.setupClearButtonWithAction
import kotlinx.android.synthetic.main.fragment_event.btNextStep
import kotlinx.android.synthetic.main.fragment_home_team.*

class HomeTeamFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputHomeTeam.setupClearButtonWithAction()

        btNextStep.setOnClickListener { this.nextScreen() }
    }

    private fun nextScreen() {

        this.sendHomeTeamName()

        val ft = activity?.supportFragmentManager?.beginTransaction()
        ft?.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        ft?.replace(R.id.containerGame, AwayTeamFragment())
        ft?.addToBackStack(null)
        ft?.commit()
    }

    private fun sendHomeTeamName() {
        val intent = Intent("FILTER_HOME_TEAM")
        intent.putExtra("home_team", inputHomeTeam.text.toString())
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }

}
