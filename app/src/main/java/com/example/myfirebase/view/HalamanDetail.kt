package com.example.myfirebase.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirebase.R
import com.example.myfirebase.modeldata.Siswa
import com.example.myfirebase.view.route.DestinasiDetail
import com.example.myfirebase.viewmodel.DetailViewModel
import com.example.myfirebase.viewmodel.PenyediaViewModel
import com.example.myfirebase.viewmodel.StatusUIDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDetail(
    navigateBack: () -> Unit,
    navigateToEditItem: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiDetail.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditItem(viewModel.statusUIDetail.let {
                    if (it is StatusUIDetail.Success) it.satusiswa.id.toString() else ""
                }) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit_siswa)
                )
            }
        },
    ) { innerPadding ->
        StatusDetailScreen(
            statusUIDetail = viewModel.statusUIDetail,
            retryAction = viewModel::getSatuSiswa,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            onDeleteClick = {
                viewModel.hapusSatuSiswa()
                navigateBack()
            }
        )
    }
}

@Composable
fun StatusDetailScreen(
    statusUIDetail: StatusUIDetail,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    when (statusUIDetail) {
        is StatusUIDetail.Loading -> OnLoading(modifier = modifier)
        is StatusUIDetail.Success -> ItemDetailSiswa(
            siswa = statusUIDetail.satusiswa,
            modifier = modifier,
            onDeleteClick = onDeleteClick
        )
        is StatusUIDetail.Error -> OnError(retryAction, modifier = modifier)
    }
}

@Composable
fun ItemDetailSiswa(
    modifier: Modifier = Modifier,
    siswa: Siswa,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            ItemDetailRow(
                labelResID = R.string.nama,
                itemDetail = siswa.nama,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
            ItemDetailRow(
                labelResID = R.string.alamat,
                itemDetail = siswa.alamat,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
            ItemDetailRow(
                labelResID = R.string.telpon,
                itemDetail = siswa.telpon,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )

            OutlinedButton(
                onClick = { deleteConfirmationRequired = true },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.delete))
            }
            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        onDeleteClick()
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
        }
    }
}

@Composable
fun ItemDetailRow(
    labelResID: Int,
    itemDetail: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}
