package com.mentarey.android.samples.recycler

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mentarey.android.samples.R

//class LogsAdapter internal constructor(private val logEventsList: ArrayList<LogEvent> = arrayListOf()) :
//    RecyclerView.Adapter<LogsAdapter.SimpleLogViewHolder>() {
//
//    inner class SimpleLogViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
//        override val containerView: View
//            get() = itemView
//
//        fun bind(logEvent: LogEvent) {
//            containerView.run {
//                textView_event_date.text = logEvent.eventDate
//                textView_task_name.text = logEvent.taskName
//                textView_event_text.text = logEvent.logText
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleLogViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.simple_log_element, parent, false)
//        return SimpleLogViewHolder(itemView)
//    }
//
//    override fun getItemCount(): Int = logEventsList.size
//
//    override fun onBindViewHolder(holder: SimpleLogViewHolder, position: Int) {
//        holder.bind(logEventsList[position])
//    }
//
//    fun removeAt(position: Int) {
//        logEventsList.removeAt(position)
//        notifyItemRemoved(position)
//    }
//}

//private fun setUpLogsRecyclerView() {
//    logs_recycler_view.setHasFixedSize(true)
//    logs_recycler_view.layoutManager = LinearLayoutManager(requireContext())
//    logs_recycler_view.adapter = logsAdapter
//
//    val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            val adapter = logs_recycler_view.adapter as LogsAdapter
//            val removePosition = viewHolder.adapterPosition
//            adapter.removeAt(removePosition)
//        }
//    }
//    val itemTouchHelper = ItemTouchHelper(swipeHandler)
//    itemTouchHelper.attachToRecyclerView(logs_recycler_view)
//}

abstract class SwipeToDeleteCallback(context: Context) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon =
        ContextCompat.getDrawable(context, R.drawable.baseline_delete_white_36) as Drawable
    private val intrinsicWidth = deleteIcon.intrinsicWidth
    private val intrinsicHeight = deleteIcon.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(
                c,
                itemView.right + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        // Draw the red delete background
        background.color = backgroundColor
        background.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(c)

        // Calculate position of delete icon
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        // Draw the delete icon
        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }

    /**
     * Can ignore some elements (0 - disable movement fot element)
     * if (viewHolder.itemViewType == Adapter.SOME_TYPE) return 0
     * if (viewHolder.adapterPosition == 0) return 0
     *
     * override fun getMovementFlags(
     *      recyclerView: RecyclerView,
     *      viewHolder: RecyclerView.ViewHolder
     * ): Int {
     *      if (viewHolder.adapterPosition == 10) return 0
     *      return super.getMovementFlags(recyclerView, viewHolder)
     * }
     */
}