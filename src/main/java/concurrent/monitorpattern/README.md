# Tracker的问题

UnsafeTracker的问题在于，当非深度拷贝时，getLocations返回的是原来的Map，Map内部的Point对象是
可变的，因此，可能被外部修改状态导致出现线程安全问题，想要变成线程安全的，要么深度拷贝，
要么对对象作不可变处理,Java Monitor Pattern的核心就是，识别所有的可能被多线程访问的内容，
并通过封装等方式将其变为不可变的
