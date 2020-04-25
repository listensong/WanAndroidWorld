package com.song.example.wanandroid.base.job

import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.Exceptions
import io.reactivex.internal.util.ExceptionHelper
import io.reactivex.internal.util.OpenHashSet
import kotlinx.coroutines.Job
import java.util.*

/**
 * @author: Listensong
 * @time 19-10-22 下午9:09
 * @desc com.song.example.wanandroid.base.job.CompositeJob
 */
class CompositeJob(vararg jobs: Job) : Disposable {

    private var resources: OpenHashSet<Job>? = null
    @Volatile
    private var disposed: Boolean = false

    init {
        this.resources = OpenHashSet<Job>(jobs.size + 1)
        for (d in jobs) {
            this.resources?.add(d)
        }
    }

    fun add(job: Job): Boolean {
        if (!disposed) {
            synchronized(this) {
                if (!disposed) {
                    var set: OpenHashSet<Job>? = resources
                    if (set == null) {
                        set = OpenHashSet()
                        resources = set
                    }
                    set.add(job)
                    return true
                }
            }
        }
        job.cancel()
        return false
    }

    fun clear() {
        if (disposed) {
            return
        }
        var set: OpenHashSet<Job>?
        synchronized(this) {
            if (disposed) {
                return
            }

            set = resources
            resources = null
        }

        dispose(set)
    }

    override fun isDisposed(): Boolean {
        return disposed
    }

    override fun dispose() {
        if (disposed) {
            return
        }
        var set: OpenHashSet<Job>?
        synchronized(this) {
            if (disposed) {
                return
            }
            disposed = true
            set = resources
            resources = null
        }

        dispose(set)
    }

    private fun dispose(set: OpenHashSet<Job>?) {
        if (set == null) {
            return
        }
        var errors: MutableList<Throwable>? = null
        val array = set.keys()
        for (o in array) {
            if (o is Job) {
                try {
                    o.cancel()
                } catch (ex: Throwable) {
                    Exceptions.throwIfFatal(ex)
                    if (errors == null) {
                        errors = ArrayList()
                    }
                    errors.add(ex)
                }

            }
        }
        if (errors != null) {
            if (errors.size == 1) {
                throw ExceptionHelper.wrapOrThrow(errors[0])
            }
            throw CompositeException(errors)
        }
    }

    /**
     * Removes and disposes the given disposable if it is part of this
     * container.
     * @param job the disposable to remove and dispose, not null
     * @return true if the operation was successful
     */
    fun remove(job: Job): Boolean {
        if (delete(job)) {
            job.cancel()
            return true
        }
        return false
    }

    /**
     * Removes (but does not dispose) the given disposable if it is part of this
     * container.
     * @param job the disposable to remove, not null
     * @return true if the operation was successful
     * @throws NullPointerException if `disposable` is null
     */
    fun delete(job: Job): Boolean {
        if (disposed) {
            return false
        }
        synchronized(this) {
            if (disposed) {
                return false
            }

            val set = resources
            if (set == null || !set.remove(job)) {
                return false
            }
        }
        return true
    }

    /**
     * Returns the number of currently held Jobs.
     * @return the number of currently held Jobs
     */
    fun size(): Int {
        if (disposed) {
            return 0
        }
        synchronized(this) {
            if (disposed) {
                return 0
            }
            return resources?.size() ?: 0
        }
    }
}