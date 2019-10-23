# InfinityScroll  

## JsonPlaceHolder API를 이용하여 무한스크롤 구현하기  
[무한스크롤](https://en.wiktionary.org/wiki/infinite_scroll)은 무한스크롤, 즉 유튜브나 페이스북, 트위터 등에서 스크롤이 끊임없이 이어지는 것  
[JsonPlaceHolder](https://jsonplaceholder.typicode.com/)을 사용하여 무한스크롤 구현하기  

<br/>

## JsonPlaceHolder API Routes
All HTTP methods are supported.  
<br/>
 
GET	/posts  
GET	/posts/1  
GET	/posts/1/comments  
GET	/comments?postId=1  
GET	/posts?userId=1  
POST	/posts  
PUT	/posts/1  
PATCH	/posts/1  
DELETE	/posts/1  


<br/>
<br/>


## Used Stack  
#### Retrofit, Gson, RxJava(RxKotlin,RxAndroid)

<br/>

## Example  
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

<br/>

## Image  
#### InfinityScrollPage  
<img src="https://user-images.githubusercontent.com/39984656/67356898-bc9fce80-f596-11e9-8bec-cc812a9f56af.png" width="300" height="450">  

#### DetailPage  
<img src="https://user-images.githubusercontent.com/39984656/67357015-f2dd4e00-f596-11e9-80d8-98f62829c3ad.png" width="300" height="450">

