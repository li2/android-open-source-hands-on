
# Creating a View Class

## Define Custom Attributes


tip：attr 文件名称；相同名称；



## Apply Custom Attributes

## Add Properties and Events



- What to draw, handled by Canvas
- How to draw, handled by Paint.


Calculate positions, dimensions, and any other values related to your view's size in onSizeChanged(), instead of recalculating them every time you draw.

如果在 `onDraw(...)` 中调用 `new RectF(...)` 会出现如下警告：

> Avoid object allocations during draw/layout operations (preallocate and reuse instead).
> You should avoid allocating objects during a drawing or layout operation. These are called frequently, so a smooth UI can be interrupted by garbage collection pauses caused by the object allocations.  The way this is generally handled is to allocate the needed objects up front and to reuse them for each drawing operation.  Some methods allocate memory on your behalf (such as Bitmap.create), and these should be handled in the same way.
