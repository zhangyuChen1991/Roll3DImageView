Roll3DImageView
----
#####简介
>这是一个实现图片3D翻转的自定义view,包含四种3D翻转的效果以及一种2D平移效果。

####效果
**2D平移：**

![](https://github.com/zhangyuChen1991/some_sources/blob/master/3DView/2d.gif)

**3D翻转：**

![](https://github.com/zhangyuChen1991/some_sources/blob/master/3DView/whole3D.gif)

**3D开合翻转：**

![](https://github.com/zhangyuChen1991/some_sources/blob/master/3DView/spe.gif)

**百叶窗：**

![](https://github.com/zhangyuChen1991/some_sources/blob/master/3DView/byc.gif)

**轮转效果：**

![](https://github.com/zhangyuChen1991/some_sources/blob/master/3DView/inturn.gif)


####使用
>你可能乍一看以为它是一个ViewGroup的子类，其实不然，它是继承View实现的。并且，只有一个类，
类的名字叫：Roll3DView。在你想要使用的地方把它拷贝过去，调用API使用就行。

####API
>* addImageBitmap(Bitmap bitmap)  添加bitmap
>
>* removeBitmapAt(int index)  删除在index位置上的bitmap
>
>* setRollMode(RollMode rollMode)  设置滚动模式
>
>* setRollDirection(int direction)  设置滚动方向:1为竖直方向,其他为水平方向;
>
>* toPre() 播放前一张
>
>* toNext() 播放后一张
>
>* setRollDuration(int rollDuration)  自动滚动设置单次滚动的时间
>
>* setPartNumber(int partNumber)  在百叶窗、轮转、3D开合模式时设置分割的块数


####实现原理
>相关原理和实现过程请参见[这里](http://www.jianshu.com/p/e070fa69eb1d)



最后，如果有相关问题需要详细讨论，也欢迎加群430352053，方便交流，谢谢！

