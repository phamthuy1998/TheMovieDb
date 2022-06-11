package com.thuypham.ptithcm.baseapp.ui.adapter


import android.view.View
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.data.remote.response.Movie
import com.thuypham.ptithcm.baseapp.databinding.ItemMovieBinding

class MovieItemViewHolder(private val binding: ItemMovieBinding) : BaseViewHolder<Movie>(binding.root) {

    override fun bind(item: Movie) {
        binding.apply {
            tvMovieName.text = item.title
            tvRate.text = item.voteAverage.toString()
            Glide.with(itemView.context)
                .load(item.getImagePath())
                .into(ivMovie)

        }
    }
}

class MovieItemViewHolderFactory : ViewHolderFactory {
    override fun layoutId(): Int {
        return R.layout.item_movie
    }

    override fun createViewHolder(viewItem: View): BaseViewHolder<*> {
        return MovieItemViewHolder(ItemMovieBinding.bind(viewItem))
    }

    override fun bindViewHolder(viewHolder: BaseViewHolder<*>, itemModel: ItemModel) {
        (viewHolder as MovieItemViewHolder).bind(itemModel as Movie)
    }

}