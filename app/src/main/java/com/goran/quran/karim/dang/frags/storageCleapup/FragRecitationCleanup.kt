package com.goran.quran.karim.dang.frags.storageCleapup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.goran.quran.karim.dang.R
import com.goran.quran.karim.dang.adapters.storageCleanup.ADPRecitationCleanup
import com.goran.quran.karim.dang.components.storageCleanup.RecitationCleanupItemModel
import com.goran.quran.karim.dang.databinding.FragStorageCleanupBinding
import com.goran.quran.karim.dang.utils.reader.recitation.RecitationManager
import com.goran.quran.karim.dang.utils.univ.FileUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragRecitationCleanup : FragStorageCleanupBase() {
    private lateinit var fileUtils: FileUtils

    override fun getFragTitle(ctx: Context) = ctx.getString(R.string.strTitleRecitations)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_storage_cleanup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fileUtils = FileUtils.newInstance(view.context)

        init(FragStorageCleanupBinding.bind(view))
    }

    private fun init(binding: FragStorageCleanupBinding) {
        binding.loader.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            RecitationManager.prepare(binding.root.context, false) {
                val reciterItems = ArrayList<RecitationCleanupItemModel>()

                fileUtils.recitationDir.listFiles()?.filter { it.isDirectory }
                    ?.forEach { reciterDir ->
                        val downloadsCount = reciterDir.listFiles()?.filter { it.isFile }?.size ?: 0
                        if (downloadsCount > 0) {
                            val item = RecitationCleanupItemModel(
                                recitationModel = RecitationManager.getModel(reciterDir.name)
                                    ?: RecitationManager.emptyModel(
                                        slug = reciterDir.name,
                                        reciter = reciterDir.name
                                    ),
                                downloadsCount = downloadsCount
                            )

                            reciterItems.add(item)
                        }
                    }

                CoroutineScope(Dispatchers.Main).launch {
                    populateReciters(binding, reciterItems)
                }
            }
        }
    }

    private fun populateReciters(
        binding: FragStorageCleanupBinding,
        items: List<RecitationCleanupItemModel>
    ) {
        val mAdapter = ADPRecitationCleanup(items, fileUtils)
        binding.list.adapter = mAdapter
        binding.list.layoutManager = LinearLayoutManager(binding.root.context)

        binding.loader.visibility = View.GONE
    }
}
