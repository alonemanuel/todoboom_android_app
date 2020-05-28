package com.example.todoboom.todoadder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoboom.MyName
import com.example.todoboom.R
import com.example.todoboom.database.TodoDatabase
import com.example.todoboom.database.TodoItem
import com.example.todoboom.databinding.FragmentAdderBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [AdderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdderFragment : Fragment() {
    val myName: MyName = MyName("Alon Emanuel")
    private lateinit var binding: FragmentAdderBinding
    private lateinit var adderViewModel: AdderViewModel
    private lateinit var adapter: TodoItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_adder, container, false
        )


        val application = requireNotNull(this.activity).application
        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        val viewModelFactory = AdderViewModelFactory(dataSource, application)
        adderViewModel =
            ViewModelProvider(this, viewModelFactory).get(AdderViewModel::class.java)

        adapter = TodoItemAdapter(
            adderViewModel,
            TodoItemListener({ todo -> todoPressed(todo) }, { todo -> todoHold(todo) })
        )

        binding.myName = myName
        binding.adderViewModel = adderViewModel
        binding.setLifecycleOwner(this)
        binding.todosList.adapter = adapter

        adderViewModel.todos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                Timber.i("The todo list size is: ${it.size}")

            }
        })


//        logTodoLength()
        setOnClickListeners(adderViewModel)
        return binding.root
    }

    fun todoPressed(todo: TodoItem) {
//        var todo: TodoItem? = null
//        todo=adderViewModel.getTodo(todoId)

        todo.endTimeMilli = System.currentTimeMillis()
        Toast.makeText(
            context,
            "Todo ${todo?.todoDesc} completed. Boom!!",
            Toast.LENGTH_LONG
        ).show()
    }

    fun todoHold(todo: TodoItem) {

    }

    private fun logTodoLength() {
        var todosLength = adderViewModel.todos.value?.size
//        var todosLength = adderViewModel.numberOfTodos
        Timber.i("The todo list size is: $todosLength")
    }


    private fun setOnClickListeners(adderViewModel: AdderViewModel) {
        binding.apply {
            createButton.setOnClickListener { addToDo(adderViewModel) }
            doneButton.setOnClickListener { addNickname(it) }
            nicknameView.setOnClickListener { updateNickname() }
            seeStatsButton.setOnClickListener { navigateToInfoFragment() }
        }

    }

    private fun navigateToInfoFragment() {
//        binding.apply { findNavController().navigate(AdderFragmentDirections.actionAdderFragmentToInfoFragment()) }
    }

    private fun updateNickname() {

        binding.apply {
            nicknameEdit.visibility = View.VISIBLE
            doneButton.visibility = View.VISIBLE
            nicknameView.visibility = View.GONE
            nicknameEdit.requestFocus()
        }
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.nicknameEdit, 0)

    }

    private fun addNickname(view: View) {

        binding.apply {
            myName?.nickname = nicknameEdit.text.toString()
            invalidateAll()
            nicknameEdit.visibility = View.GONE
            doneButton.visibility = View.GONE
            nicknameView.visibility = View.VISIBLE
        }


        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    private fun addToDo(adderViewModel: AdderViewModel) {
        val todoInput = binding.todoInput.text.toString()
        Timber.i(todoInput)
        if (todoInput == "") {
            Toast.makeText(context, getString(R.string.empty_todo_err), Toast.LENGTH_LONG).show()

        } else {
            adderViewModel.onCreateTodo(todoInput)
            binding.todoInput.text.clear()
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }
}
