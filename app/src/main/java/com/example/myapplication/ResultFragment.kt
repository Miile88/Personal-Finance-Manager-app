package com.example.myapplication
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_FINANCE_DATA = "finance_data"

        fun newInstance(financeModel: FinanceModel): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putParcelable(ARG_FINANCE_DATA, financeModel)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moTxtFullName.text = "Full Name: Milena Oganiani"
        binding.moTxtBirthYear.text = "Birth Year: 2004"

        val data = arguments?.getParcelable<FinanceModel>(ARG_FINANCE_DATA)
        data?.let {
            val currencyFormat = java.text.NumberFormat.getCurrencyInstance()
            
            val resultText = StringBuilder()
                .append(getString(R.string.label_salary)).append(currencyFormat.format(it.salary)).append("\n")
                .append(getString(R.string.label_rent)).append(currencyFormat.format(it.rent)).append("\n")
                .append(getString(R.string.label_food)).append(currencyFormat.format(it.food)).append("\n")
                .append(getString(R.string.label_total_expenses)).append(currencyFormat.format(it.totalExpenses)).append("\n")
                .append(getString(R.string.label_remaining_balance)).append(currencyFormat.format(it.remainingBalance)).append("\n")
                .append(getString(R.string.label_recommended_savings)).append(currencyFormat.format(it.recommendedSavings))
                .toString()

            binding.moTxtResultDetails.text = resultText

            if (it.isDeficit) {
                binding.moTxtResultDetails.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.error))
            } else {
                binding.moTxtResultDetails.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.success))
            }
        }

        binding.moBtnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}