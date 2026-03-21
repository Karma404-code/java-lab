// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package java.lang;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.StructureViolationException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import jdk.internal.event.ThreadSleepEvent;
import jdk.internal.misc.TerminatingThreadLocal;
import jdk.internal.misc.Unsafe;
import jdk.internal.misc.VM;
import jdk.internal.reflect.CallerSensitive;
import jdk.internal.reflect.Reflection;
import jdk.internal.vm.Continuation;
import jdk.internal.vm.ScopedValueContainer;
import jdk.internal.vm.StackableScope;
import jdk.internal.vm.ThreadContainer;
import jdk.internal.vm.annotation.ForceInline;
import jdk.internal.vm.annotation.Hidden;
import jdk.internal.vm.annotation.IntrinsicCandidate;
import jdk.internal.vm.annotation.Stable;
import sun.nio.ch.Interruptible;
import sun.security.util.SecurityConstants;

public class Thread implements Runnable {
   private volatile long eetop;
   private final long tid;
   private volatile String name;
   volatile boolean interrupted;
   private volatile ClassLoader contextClassLoader;
   private AccessControlContext inheritedAccessControlContext;
   private final FieldHolder holder;
   ThreadLocal.ThreadLocalMap threadLocals;
   ThreadLocal.ThreadLocalMap inheritableThreadLocals;
   private Object scopedValueBindings;
   private static final Object NEW_THREAD_BINDINGS;
   final Object interruptLock;
   private volatile Object parkBlocker;
   volatile Interruptible nioBlocker;
   public static final int MIN_PRIORITY = 1;
   public static final int NORM_PRIORITY = 5;
   public static final int MAX_PRIORITY = 10;
   private Continuation cont;
   static final int NO_INHERIT_THREAD_LOCALS = 4;
   private static final StackTraceElement[] EMPTY_STACK_TRACE;
   private volatile UncaughtExceptionHandler uncaughtExceptionHandler;
   private static volatile UncaughtExceptionHandler defaultUncaughtExceptionHandler;
   long threadLocalRandomSeed;
   int threadLocalRandomProbe;
   int threadLocalRandomSecondarySeed;
   @Stable
   private ThreadContainer container;
   private volatile StackableScope headStackableScopes;

   private static native void registerNatives();

   static Object scopedValueBindings() {
      return currentThread().scopedValueBindings;
   }

   static void setScopedValueBindings(Object bindings) {
      currentThread().scopedValueBindings = bindings;
   }

   @IntrinsicCandidate
   static native Object findScopedValueBindings();

   void inheritScopedValueBindings(ThreadContainer container) {
      ScopedValueContainer.BindingsSnapshot snapshot;
      if (container.owner() != null && (snapshot = container.scopedValueBindings()) != null) {
         Object bindings = snapshot.scopedValueBindings();
         if (currentThread().scopedValueBindings != bindings) {
            throw new StructureViolationException("Scoped value bindings have changed");
         }

         this.scopedValueBindings = bindings;
      }

   }

   static void blockedOn(Interruptible b) {
      Thread me = currentThread();
      synchronized(me.interruptLock) {
         me.nioBlocker = b;
      }
   }

   Continuation getContinuation() {
      return this.cont;
   }

   void setContinuation(Continuation cont) {
      this.cont = cont;
   }

   @IntrinsicCandidate
   static native Thread currentCarrierThread();

   @IntrinsicCandidate
   public static native Thread currentThread();

   @IntrinsicCandidate
   native void setCurrentThread(Thread var1);

   @IntrinsicCandidate
   static native Object[] scopedValueCache();

   @IntrinsicCandidate
   static native void setScopedValueCache(Object[] var0);

   @IntrinsicCandidate
   static native void ensureMaterializedForStackWalk(Object var0);

   public static void yield() {
      Thread var1 = currentThread();
      if (var1 instanceof VirtualThread vthread) {
         vthread.tryYield();
      } else {
         yield0();
      }

   }

   private static native void yield0();

   private static ThreadSleepEvent beforeSleep(long nanos) {
      ThreadSleepEvent event = null;
      if (ThreadSleepEvent.isTurnedOn()) {
         try {
            event = new ThreadSleepEvent();
            event.time = nanos;
            event.begin();
         } catch (OutOfMemoryError var4) {
            event = null;
         }
      }

      return event;
   }

   private static void afterSleep(ThreadSleepEvent event) {
      if (event != null) {
         try {
            event.commit();
         } catch (OutOfMemoryError var2) {
         }
      }

   }

   public static void sleep(long millis) throws InterruptedException {
      if (millis < 0L) {
         throw new IllegalArgumentException("timeout value is negative");
      } else {
         long nanos = TimeUnit.MILLISECONDS.toNanos(millis);
         ThreadSleepEvent event = beforeSleep(nanos);

         try {
            Thread var6 = currentThread();
            if (var6 instanceof VirtualThread) {
               VirtualThread vthread = (VirtualThread)var6;
               vthread.sleepNanos(nanos);
            } else {
               sleep0(nanos);
            }
         } finally {
            afterSleep(event);
         }

      }
   }

   private static native void sleep0(long var0) throws InterruptedException;

   public static void sleep(long millis, int nanos) throws InterruptedException {
      if (millis < 0L) {
         throw new IllegalArgumentException("timeout value is negative");
      } else if (nanos >= 0 && nanos <= 999999) {
         long totalNanos = TimeUnit.MILLISECONDS.toNanos(millis);
         totalNanos += Math.min(Long.MAX_VALUE - totalNanos, (long)nanos);
         ThreadSleepEvent event = beforeSleep(totalNanos);

         try {
            Thread var7 = currentThread();
            if (var7 instanceof VirtualThread) {
               VirtualThread vthread = (VirtualThread)var7;
               vthread.sleepNanos(totalNanos);
            } else {
               sleep0(totalNanos);
            }
         } finally {
            afterSleep(event);
         }

      } else {
         throw new IllegalArgumentException("nanosecond timeout value out of range");
      }
   }

   public static void sleep(Duration duration) throws InterruptedException {
      long nanos = TimeUnit.NANOSECONDS.convert(duration);
      if (nanos >= 0L) {
         ThreadSleepEvent event = beforeSleep(nanos);

         try {
            Thread var5 = currentThread();
            if (var5 instanceof VirtualThread) {
               VirtualThread vthread = (VirtualThread)var5;
               vthread.sleepNanos(nanos);
            } else {
               sleep0(nanos);
            }
         } finally {
            afterSleep(event);
         }

      }
   }

   @IntrinsicCandidate
   public static void onSpinWait() {
   }

   private static ClassLoader contextClassLoader(Thread parent) {
      SecurityManager sm = System.getSecurityManager();
      return sm != null && !isCCLOverridden(parent.getClass()) ? parent.contextClassLoader : parent.getContextClassLoader();
   }

   Thread(ThreadGroup g, String name, int characteristics, Runnable task, long stackSize, AccessControlContext acc) {
      this.interruptLock = new Object();
      Thread parent = currentThread();
      boolean attached = parent == this;
      if (attached) {
         if (g == null) {
            throw new InternalError("group cannot be null when attaching");
         }

         this.holder = new FieldHolder(g, task, stackSize, 5, false);
      } else {
         SecurityManager sm = System.getSecurityManager();
         if (g == null) {
            if (sm != null) {
               g = sm.getThreadGroup();
            }

            if (g == null) {
               g = parent.getThreadGroup();
            }
         }

         if (sm != null) {
            sm.checkAccess(g);
            if (isCCLOverridden(this.getClass())) {
               sm.checkPermission(SecurityConstants.SUBCLASS_IMPLEMENTATION_PERMISSION);
            }
         }

         int priority = Math.min(parent.getPriority(), g.getMaxPriority());
         this.holder = new FieldHolder(g, task, stackSize, priority, parent.isDaemon());
      }

      if (attached && VM.initLevel() < 1) {
         this.tid = 1L;
      } else {
         this.tid = Thread.ThreadIdentifiers.next();
      }

      this.name = name != null ? name : genThreadName();
      if (acc != null) {
         this.inheritedAccessControlContext = acc;
      } else {
         this.inheritedAccessControlContext = AccessController.getContext();
      }

      if (!attached) {
         if ((characteristics & 4) == 0) {
            ThreadLocal.ThreadLocalMap parentMap = parent.inheritableThreadLocals;
            if (parentMap != null && parentMap.size() > 0) {
               this.inheritableThreadLocals = ThreadLocal.createInheritedMap(parentMap);
            }

            if (VM.isBooted()) {
               this.contextClassLoader = contextClassLoader(parent);
            }
         } else if (VM.isBooted()) {
            this.contextClassLoader = ClassLoader.getSystemClassLoader();
         }
      }

      this.scopedValueBindings = NEW_THREAD_BINDINGS;
   }

   Thread(String name, int characteristics, boolean bound) {
      this.interruptLock = new Object();
      this.tid = Thread.ThreadIdentifiers.next();
      this.name = name != null ? name : "";
      this.inheritedAccessControlContext = Thread.Constants.NO_PERMISSIONS_ACC;
      if ((characteristics & 4) == 0) {
         Thread parent = currentThread();
         ThreadLocal.ThreadLocalMap parentMap = parent.inheritableThreadLocals;
         if (parentMap != null && parentMap.size() > 0) {
            this.inheritableThreadLocals = ThreadLocal.createInheritedMap(parentMap);
         }

         this.contextClassLoader = contextClassLoader(parent);
      } else {
         this.contextClassLoader = ClassLoader.getSystemClassLoader();
      }

      this.scopedValueBindings = NEW_THREAD_BINDINGS;
      if (bound) {
         ThreadGroup g = Thread.Constants.VTHREAD_GROUP;
         int pri = 5;
         this.holder = new FieldHolder(g, (Runnable)null, -1L, pri, true);
      } else {
         this.holder = null;
      }

   }

   public static Builder.OfPlatform ofPlatform() {
      return new ThreadBuilders.PlatformThreadBuilder();
   }

   public static Builder.OfVirtual ofVirtual() {
      return new ThreadBuilders.VirtualThreadBuilder();
   }

   protected Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   static String genThreadName() {
      return "Thread-" + Thread.ThreadNumbering.next();
   }

   private static String checkName(String name) {
      if (name == null) {
         throw new NullPointerException("'name' is null");
      } else {
         return name;
      }
   }

   public Thread() {
      this((ThreadGroup)null, (String)null, 0, (Runnable)null, 0L, (AccessControlContext)null);
   }

   public Thread(Runnable task) {
      this((ThreadGroup)null, (String)null, 0, task, 0L, (AccessControlContext)null);
   }

   Thread(Runnable task, AccessControlContext acc) {
      this((ThreadGroup)null, (String)null, 0, task, 0L, acc);
   }

   public Thread(ThreadGroup group, Runnable task) {
      this(group, (String)null, 0, task, 0L, (AccessControlContext)null);
   }

   public Thread(String name) {
      this((ThreadGroup)null, checkName(name), 0, (Runnable)null, 0L, (AccessControlContext)null);
   }

   public Thread(ThreadGroup group, String name) {
      this(group, checkName(name), 0, (Runnable)null, 0L, (AccessControlContext)null);
   }

   public Thread(Runnable task, String name) {
      this((ThreadGroup)null, checkName(name), 0, task, 0L, (AccessControlContext)null);
   }

   public Thread(ThreadGroup group, Runnable task, String name) {
      this(group, checkName(name), 0, task, 0L, (AccessControlContext)null);
   }

   public Thread(ThreadGroup group, Runnable task, String name, long stackSize) {
      this(group, checkName(name), 0, task, stackSize, (AccessControlContext)null);
   }

   public Thread(ThreadGroup group, Runnable task, String name, long stackSize, boolean inheritInheritableThreadLocals) {
      this(group, checkName(name), inheritInheritableThreadLocals ? 0 : 4, task, stackSize, (AccessControlContext)null);
   }

   public static Thread startVirtualThread(Runnable task) {
      Objects.requireNonNull(task);
      Thread thread = ThreadBuilders.newVirtualThread((Executor)null, (String)null, 0, task);
      thread.start();
      return thread;
   }

   public final boolean isVirtual() {
      return this instanceof BaseVirtualThread;
   }

   public void start() {
      synchronized(this) {
         if (this.holder.threadStatus != 0) {
            throw new IllegalThreadStateException();
         } else {
            this.start0();
         }
      }
   }

   void start(ThreadContainer container) {
      synchronized(this) {
         if (this.holder.threadStatus != 0) {
            throw new IllegalThreadStateException();
         } else if (this.container != null) {
            throw new IllegalThreadStateException();
         } else {
            this.setThreadContainer(container);
            boolean started = false;
            container.onStart(this);

            try {
               this.inheritScopedValueBindings(container);
               this.start0();
               started = true;
            } finally {
               if (!started) {
                  container.onExit(this);
               }

            }

         }
      }
   }

   private native void start0();

   public void run() {
      Runnable task = this.holder.task;
      if (task != null) {
         Object bindings = scopedValueBindings();
         this.runWith(bindings, task);
      }

   }

   @Hidden
   @ForceInline
   final void runWith(Object bindings, Runnable op) {
      ensureMaterializedForStackWalk(bindings);
      op.run();
      Reference.reachabilityFence(bindings);
   }

   void clearReferences() {
      this.threadLocals = null;
      this.inheritableThreadLocals = null;
      this.inheritedAccessControlContext = null;
      if (this.uncaughtExceptionHandler != null) {
         this.uncaughtExceptionHandler = null;
      }

      if (this.nioBlocker != null) {
         this.nioBlocker = null;
      }

   }

   private void exit() {
      boolean var9 = false;

      try {
         var9 = true;
         if (this.headStackableScopes != null) {
            StackableScope.popAll();
            var9 = false;
         } else {
            var9 = false;
         }
      } finally {
         if (var9) {
            ThreadContainer container = this.threadContainer();
            if (container != null) {
               container.onExit(this);
            }

         }
      }

      ThreadContainer container = this.threadContainer();
      if (container != null) {
         container.onExit(this);
      }

      try {
         if (this.threadLocals != null && TerminatingThreadLocal.REGISTRY.isPresent()) {
            TerminatingThreadLocal.threadTerminated();
         }
      } finally {
         this.clearReferences();
      }

   }

   /** @deprecated */
   @Deprecated(
      since = "1.2",
      forRemoval = true
   )
   public final void stop() {
      throw new UnsupportedOperationException();
   }

   public void interrupt() {
      if (this != currentThread()) {
         this.checkAccess();
      }

      this.interrupted = true;
      this.interrupt0();
      if (this != currentThread()) {
         synchronized(this.interruptLock) {
            Interruptible b = this.nioBlocker;
            if (b != null) {
               b.interrupt(this);
            }
         }
      }

   }

   public static boolean interrupted() {
      return currentThread().getAndClearInterrupt();
   }

   public boolean isInterrupted() {
      return this.interrupted;
   }

   final void setInterrupt() {
      if (!this.interrupted) {
         this.interrupted = true;
         this.interrupt0();
      }

   }

   final void clearInterrupt() {
      if (this.interrupted) {
         this.interrupted = false;
         clearInterruptEvent();
      }

   }

   boolean getAndClearInterrupt() {
      boolean oldValue = this.interrupted;
      if (oldValue) {
         this.interrupted = false;
         clearInterruptEvent();
      }

      return oldValue;
   }

   public final boolean isAlive() {
      return this.alive();
   }

   boolean alive() {
      return this.eetop != 0L;
   }

   /** @deprecated */
   @Deprecated(
      since = "1.2",
      forRemoval = true
   )
   public final void suspend() {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated(
      since = "1.2",
      forRemoval = true
   )
   public final void resume() {
      throw new UnsupportedOperationException();
   }

   public final void setPriority(int newPriority) {
      this.checkAccess();
      if (newPriority <= 10 && newPriority >= 1) {
         if (!this.isVirtual()) {
            this.priority(newPriority);
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   void priority(int newPriority) {
      ThreadGroup g = this.holder.group;
      if (g != null) {
         int maxPriority = g.getMaxPriority();
         if (newPriority > maxPriority) {
            newPriority = maxPriority;
         }

         this.setPriority0(this.holder.priority = newPriority);
      }

   }

   public final int getPriority() {
      return this.isVirtual() ? 5 : this.holder.priority;
   }

   public final synchronized void setName(String name) {
      this.checkAccess();
      if (name == null) {
         throw new NullPointerException("name cannot be null");
      } else {
         this.name = name;
         if (!this.isVirtual() && currentThread() == this) {
            this.setNativeName(name);
         }

      }
   }

   public final String getName() {
      return this.name;
   }

   public final ThreadGroup getThreadGroup() {
      if (this.isTerminated()) {
         return null;
      } else {
         return this.isVirtual() ? virtualThreadGroup() : this.holder.group;
      }
   }

   public static int activeCount() {
      return currentThread().getThreadGroup().activeCount();
   }

   public static int enumerate(Thread[] tarray) {
      return currentThread().getThreadGroup().enumerate(tarray);
   }

   /** @deprecated */
   @Deprecated(
      since = "1.2",
      forRemoval = true
   )
   public int countStackFrames() {
      throw new UnsupportedOperationException();
   }

   public final void join(long millis) throws InterruptedException {
      if (millis < 0L) {
         throw new IllegalArgumentException("timeout value is negative");
      } else {
         long startTime;
         if (this instanceof VirtualThread) {
            VirtualThread vthread = (VirtualThread)this;
            if (this.isAlive()) {
               startTime = TimeUnit.MILLISECONDS.toNanos(millis);
               vthread.joinNanos(startTime);
            }

         } else {
            synchronized(this) {
               if (millis > 0L) {
                  if (this.isAlive()) {
                     startTime = System.nanoTime();
                     long delay = millis;

                     do {
                        this.wait(delay);
                     } while(this.isAlive() && (delay = millis - TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)) > 0L);
                  }
               } else {
                  while(this.isAlive()) {
                     this.wait(0L);
                  }
               }

            }
         }
      }
   }

   public final void join(long millis, int nanos) throws InterruptedException {
      if (millis < 0L) {
         throw new IllegalArgumentException("timeout value is negative");
      } else if (nanos >= 0 && nanos <= 999999) {
         if (this instanceof VirtualThread) {
            VirtualThread vthread = (VirtualThread)this;
            if (this.isAlive()) {
               long totalNanos = TimeUnit.MILLISECONDS.toNanos(millis);
               totalNanos += Math.min(Long.MAX_VALUE - totalNanos, (long)nanos);
               vthread.joinNanos(totalNanos);
            }

         } else {
            if (nanos > 0 && millis < Long.MAX_VALUE) {
               ++millis;
            }

            this.join(millis);
         }
      } else {
         throw new IllegalArgumentException("nanosecond timeout value out of range");
      }
   }

   public final void join() throws InterruptedException {
      this.join(0L);
   }

   public final boolean join(Duration duration) throws InterruptedException {
      long nanos = TimeUnit.NANOSECONDS.convert(duration);
      State state = this.threadState();
      if (state == Thread.State.NEW) {
         throw new IllegalThreadStateException("Thread not started");
      } else if (state == Thread.State.TERMINATED) {
         return true;
      } else if (nanos <= 0L) {
         return false;
      } else if (this instanceof VirtualThread) {
         VirtualThread vthread = (VirtualThread)this;
         return vthread.joinNanos(nanos);
      } else {
         long millis = TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS);
         if (nanos > TimeUnit.NANOSECONDS.convert(millis, TimeUnit.MILLISECONDS)) {
            ++millis;
         }

         this.join(millis);
         return this.isTerminated();
      }
   }

   public static void dumpStack() {
      (new Exception("Stack trace")).printStackTrace();
   }

   public final void setDaemon(boolean on) {
      this.checkAccess();
      if (this.isVirtual() && !on) {
         throw new IllegalArgumentException("'false' not legal for virtual threads");
      } else if (this.isAlive()) {
         throw new IllegalThreadStateException();
      } else {
         if (!this.isVirtual()) {
            this.daemon(on);
         }

      }
   }

   void daemon(boolean on) {
      this.holder.daemon = on;
   }

   public final boolean isDaemon() {
      return this.isVirtual() ? true : this.holder.daemon;
   }

   /** @deprecated */
   @Deprecated(
      since = "17",
      forRemoval = true
   )
   public final void checkAccess() {
      SecurityManager security = System.getSecurityManager();
      if (security != null) {
         security.checkAccess(this);
      }

   }

   public String toString() {
      StringBuilder sb = new StringBuilder("Thread[#");
      sb.append(this.threadId());
      sb.append(",");
      sb.append(this.getName());
      sb.append(",");
      sb.append(this.getPriority());
      sb.append(",");
      ThreadGroup group = this.getThreadGroup();
      if (group != null) {
         sb.append(group.getName());
      }

      sb.append("]");
      return sb.toString();
   }

   @CallerSensitive
   public ClassLoader getContextClassLoader() {
      ClassLoader cl = this.contextClassLoader;
      if (cl == null) {
         return null;
      } else {
         SecurityManager sm = System.getSecurityManager();
         if (sm != null) {
            Class<?> caller = Reflection.getCallerClass();
            ClassLoader.checkClassLoaderPermission(cl, caller);
         }

         return cl;
      }
   }

   public void setContextClassLoader(ClassLoader cl) {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new RuntimePermission("setContextClassLoader"));
      }

      this.contextClassLoader = cl;
   }

   public static native boolean holdsLock(Object var0);

   public StackTraceElement[] getStackTrace() {
      if (this != currentThread()) {
         SecurityManager security = System.getSecurityManager();
         if (security != null) {
            security.checkPermission(SecurityConstants.GET_STACK_TRACE_PERMISSION);
         }

         if (!this.isAlive()) {
            return EMPTY_STACK_TRACE;
         } else {
            StackTraceElement[] stackTrace = this.asyncGetStackTrace();
            return stackTrace != null ? stackTrace : EMPTY_STACK_TRACE;
         }
      } else {
         return (new Exception()).getStackTrace();
      }
   }

   StackTraceElement[] asyncGetStackTrace() {
      Object stackTrace = this.getStackTrace0();
      if (stackTrace == null) {
         return null;
      } else {
         StackTraceElement[] stes = (StackTraceElement[])stackTrace;
         return stes.length == 0 ? null : StackTraceElement.of(stes);
      }
   }

   private native Object getStackTrace0();

   public static Map<Thread, StackTraceElement[]> getAllStackTraces() {
      SecurityManager security = System.getSecurityManager();
      if (security != null) {
         security.checkPermission(SecurityConstants.GET_STACK_TRACE_PERMISSION);
         security.checkPermission(SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
      }

      Thread[] threads = getThreads();
      StackTraceElement[][] traces = dumpThreads(threads);
      Map<Thread, StackTraceElement[]> m = HashMap.newHashMap(threads.length);

      for(int i = 0; i < threads.length; ++i) {
         StackTraceElement[] stackTrace = traces[i];
         if (stackTrace != null) {
            m.put(threads[i], stackTrace);
         }
      }

      return m;
   }

   private static boolean isCCLOverridden(Class<?> cl) {
      return cl == Thread.class ? false : (Boolean)Thread.Caches.subclassAudits.get(cl);
   }

   private static boolean auditSubclass(Class<?> subcl) {
      Boolean result = (Boolean)AccessController.doPrivileged(new 1(subcl));
      return result;
   }

   static Thread[] getAllThreads() {
      return getThreads();
   }

   private static native StackTraceElement[][] dumpThreads(Thread[] var0);

   private static native Thread[] getThreads();

   /** @deprecated */
   @Deprecated(
      since = "19"
   )
   public long getId() {
      return this.threadId();
   }

   public final long threadId() {
      return this.tid;
   }

   public State getState() {
      return this.threadState();
   }

   State threadState() {
      return VM.toThreadState(this.holder.threadStatus);
   }

   boolean isTerminated() {
      return this.threadState() == Thread.State.TERMINATED;
   }

   public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler ueh) {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new RuntimePermission("setDefaultUncaughtExceptionHandler"));
      }

      defaultUncaughtExceptionHandler = ueh;
   }

   public static UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
      return defaultUncaughtExceptionHandler;
   }

   public UncaughtExceptionHandler getUncaughtExceptionHandler() {
      if (this.isTerminated()) {
         return null;
      } else {
         UncaughtExceptionHandler ueh = this.uncaughtExceptionHandler;
         return (UncaughtExceptionHandler)(ueh != null ? ueh : this.getThreadGroup());
      }
   }

   public void setUncaughtExceptionHandler(UncaughtExceptionHandler ueh) {
      this.checkAccess();
      this.uncaughtExceptionHandler(ueh);
   }

   void uncaughtExceptionHandler(UncaughtExceptionHandler ueh) {
      this.uncaughtExceptionHandler = ueh;
   }

   void dispatchUncaughtException(Throwable e) {
      this.getUncaughtExceptionHandler().uncaughtException(this, e);
   }

   static ThreadGroup virtualThreadGroup() {
      return Thread.Constants.VTHREAD_GROUP;
   }

   ThreadContainer threadContainer() {
      return this.container;
   }

   void setThreadContainer(ThreadContainer container) {
      this.container = container;
   }

   StackableScope headStackableScopes() {
      return this.headStackableScopes;
   }

   static void setHeadStackableScope(StackableScope scope) {
      currentThread().headStackableScopes = scope;
   }

   private native void setPriority0(int var1);

   private native void interrupt0();

   private static native void clearInterruptEvent();

   private native void setNativeName(String var1);

   private static native long getNextThreadIdOffset();

   static {
      registerNatives();
      NEW_THREAD_BINDINGS = Thread.class;
      EMPTY_STACK_TRACE = new StackTraceElement[0];
   }

   private static class FieldHolder {
      final ThreadGroup group;
      final Runnable task;
      final long stackSize;
      volatile int priority;
      volatile boolean daemon;
      volatile int threadStatus;

      FieldHolder(ThreadGroup group, Runnable task, long stackSize, int priority, boolean daemon) {
         this.group = group;
         this.task = task;
         this.stackSize = stackSize;
         this.priority = priority;
         if (daemon) {
            this.daemon = true;
         }

      }
   }

   private static class ThreadIdentifiers {
      private static final Unsafe U = Unsafe.getUnsafe();
      private static final long NEXT_TID_OFFSET = Thread.getNextThreadIdOffset();

      private ThreadIdentifiers() {
      }

      static long next() {
         return U.getAndAddLong((Object)null, NEXT_TID_OFFSET, 1L);
      }
   }

   private static class Constants {
      static final ThreadGroup VTHREAD_GROUP;
      static final AccessControlContext NO_PERMISSIONS_ACC;

      private Constants() {
      }

      static {
         1 getThreadGroup = new 1();
         ThreadGroup root = (ThreadGroup)AccessController.doPrivileged(getThreadGroup);
         VTHREAD_GROUP = new ThreadGroup(root, "VirtualThreads", 10, false);
         NO_PERMISSIONS_ACC = new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain((CodeSource)null, (PermissionCollection)null)});
      }
   }

   private static class ThreadNumbering {
      private static final Unsafe U = Unsafe.getUnsafe();
      private static final Object NEXT_BASE;
      private static final long NEXT_OFFSET;
      private static volatile int next;

      private ThreadNumbering() {
      }

      static int next() {
         return U.getAndAddInt(NEXT_BASE, NEXT_OFFSET, 1);
      }

      static {
         try {
            Field nextField = ThreadNumbering.class.getDeclaredField("next");
            NEXT_BASE = U.staticFieldBase(nextField);
            NEXT_OFFSET = U.staticFieldOffset(nextField);
         } catch (NoSuchFieldException var1) {
            throw new ExceptionInInitializerError(var1);
         }
      }
   }

   @FunctionalInterface
   public interface UncaughtExceptionHandler {
      void uncaughtException(Thread var1, Throwable var2);
   }

   public static enum State {
      NEW,
      RUNNABLE,
      BLOCKED,
      WAITING,
      TIMED_WAITING,
      TERMINATED;

      private State() {
      }
   }

   private static class Caches {
      static final ClassValue<Boolean> subclassAudits = new 1();

      private Caches() {
      }
   }

   public sealed interface Builder {
      Builder name(String var1);

      Builder name(String var1, long var2);

      Builder inheritInheritableThreadLocals(boolean var1);

      Builder uncaughtExceptionHandler(UncaughtExceptionHandler var1);

      Thread unstarted(Runnable var1);

      Thread start(Runnable var1);

      ThreadFactory factory();

      public sealed interface OfVirtual extends Builder permits ThreadBuilders.VirtualThreadBuilder {
         OfVirtual name(String var1);

         OfVirtual name(String var1, long var2);

         OfVirtual inheritInheritableThreadLocals(boolean var1);

         OfVirtual uncaughtExceptionHandler(UncaughtExceptionHandler var1);
      }

      public sealed interface OfPlatform extends Builder permits ThreadBuilders.PlatformThreadBuilder {
         OfPlatform name(String var1);

         OfPlatform name(String var1, long var2);

         OfPlatform inheritInheritableThreadLocals(boolean var1);

         OfPlatform uncaughtExceptionHandler(UncaughtExceptionHandler var1);

         OfPlatform group(ThreadGroup var1);

         OfPlatform daemon(boolean var1);

         default OfPlatform daemon() {
            return this.daemon(true);
         }

         OfPlatform priority(int var1);

         OfPlatform stackSize(long var1);
      }
   }
}
