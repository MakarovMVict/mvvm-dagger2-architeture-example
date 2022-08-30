package com.example.da_core_android.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * A [ViewModelProvider.Factory] implementation for this app that stores [ViewModel]s.
 *
 * Example fragment code:
 * ```
 * /**
 *  * Use ViewModelProviders.of().get() to get an instance of a androidx.lifecycle.ViewModel.
 *  * If one does not exist, a new one will be created.
 *  */
 * class ExampleFragment : BaseFragment() {
 *
 *  @Inject
 *  lateinit var viewModelFactory: ViewModelProvider.Factory
 *
 *  private val viewModel by lazy {
 *      ViewModelProviders.of(activity!!, viewModelFactory).get(ExampleViewModel::class.java)
 *  }
 *
 *  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
 *
 *      val binding = DataBindingUtil.inflate<FragmentExampleBinding>(
 *          inflater, R.layout.fragment_example,
 *          container, false
 *      )
 *
 *      binding.viewModel = viewModel
 *
 *      return binding.root
 *  }
 * }
 * ```
 *
 * Example view model code:
 * ```
 * /**
 *  * An injectable view model that extends from androidx.lifecycle.AndroidViewModel
 *  */
 * class SomeViewModel @Inject constructor(
 *    val app: Application,
 *    val someUseCase: SomeUseCase) : AndroidViewModel(app) {
 *      // ... view model code
 *  }
 * ```
 * @see ViewModelKey
 */
class AppViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) throw IllegalArgumentException("unknown model class $modelClass")
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}