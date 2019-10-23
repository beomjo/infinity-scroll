# InfinityScroll  

### JsonPlaceHolder API를 이용하여 무한스크롤 구현하기  
[무한스크롤](https://en.wiktionary.org/wiki/infinite_scroll)은 무한스크롤, 즉 유튜브나 페이스북, 트위터 등에서 스크롤이 끊임없이 이어지는 것  
[JsonPlaceHolder](https://jsonplaceholder.typicode.com/)을 사용하여 무한스크롤 구현하기  

<br/>
<br/>

### Used Stack  
#### Retrofit, Gson, RxJava(RxKotlin,RxAndroid)

### Image  
#### InfinityScrollPage
<img src="https://user-images.githubusercontent.com/39984656/67356699-f91efa80-f595-11e9-98bc-618583f9637c.png)
" width="300" height="450">  
#### DetailPage  
<img src="https://user-images.githubusercontent.com/39984656/67356367-ebb54080-f594-11e9-849c-e9c30da3ffef.png)
" width="300" height="450">  


### Example  
RecyclerView와 ScrollListener를 이용하여 최하단 도달시 Data Load
```
class InfinityRecyclerViewAdapter<ITEM : Any, B : ViewDataBinding>(
    layout: Int,
    bindingVariableId: Int? = null,
    val load: () -> Unit
) : BaseRecyclerViewAdapter<ITEM, B>(layout, bindingVariableId) {

    override fun getItemViewType(position: Int): Int {
        return if ((position == itemCount - 1)) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> {
        return if (viewType == VIEW_TYPE_NORMAL)
            BaseViewHolder(
                layout = layout,
                parent = parent,
                bindingVariableId = bindingVariableId
            )
        else BaseViewHolder(
            layout = R.layout.item_loading,
            parent = parent,
            bindingVariableId = BR.vm
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL)
            (holder).onBindViewHolder(items[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val pos = layoutManager.findLastCompletelyVisibleItemPosition()
                val numItems = recyclerView.adapter?.itemCount

                val offset = 1

                if (pos >= numItems!! - offset) {
                    load.invoke()
                }
            }
        })
    }
}
```

