package com.placefy.app.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.placefy.app.R
import com.placefy.app.adapters.ImageAdapter
import com.placefy.app.adapters.TaxesAdapter
import com.placefy.app.api.RetrofitHelper
import com.placefy.app.api.interfaces.PropertyAPI
import com.placefy.app.database.dao.AuthDAO
import com.placefy.app.databinding.ActivityPropertyShowBinding
import com.placefy.app.helpers.CircleTransform
import com.placefy.app.helpers.GoogleMapsHelper
import com.placefy.app.helpers.PropertyHelper
import com.placefy.app.models.data.Address
import com.placefy.app.models.data.Image
import com.placefy.app.models.data.Property
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.URLEncoder


class PropertyShowActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPropertyShowBinding.inflate(layoutInflater)
    }

    private val base by lazy {
        RetrofitHelper(this).noAuthApi
    }

    private val authDAO by lazy {
        AuthDAO(baseContext)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.includeToolbar.publicToolbar)
        verifyToken()
        setSBtnLoginEvents()
        loadProperty()
    }


    private fun setBtnLocationEvents(address: Address) {
        binding.btnLocation.setOnClickListener {
            GoogleMapsHelper(baseContext, address).showLocation()
        }
    }

    private fun setBtnRouteEvents(address: Address) {
        binding.btnRoute.setOnClickListener {
            GoogleMapsHelper(baseContext, address).showRoute()
        }
    }

    private fun setBtnWhatsappEvents(phone: String) {
        binding.btnWhatsapp.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val url = "https://api.whatsapp.com/send?phone=$phone&text=" + URLEncoder.encode(
                "MESSAGE",
                "UTF-8"
            )
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

    private fun setBtnPhoneEvents(phone: String) {
        binding.btnPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:$phone"))
            startActivity(intent)
        }
    }

    private fun setBtnMailEvents(email: String) {
        binding.btnMail.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "email", null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
    }

    private fun setBtnShareEvents(property: Property) {
        binding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, property.title)
                putExtra(Intent.EXTRA_TEXT, "https://placefybr.app/imovel/${property.id}")
                type = "text/plain"
            }
            startActivity(sendIntent)
        }
    }

    private fun setSBtnLoginEvents() {
        binding.includeToolbar.btnLogin.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    if (authDAO.show().accessToken.toString() != "")
                        AdminActivity::class.java else AuthActivity::class.java
                )
            )
        }
    }

    private fun verifyToken() {
        if (authDAO.show().accessToken.toString() != "") {
            binding.includeToolbar.btnLogin.text = "ADMIN"
            val button = binding.includeToolbar.btnLogin
            button.setCompoundDrawablesWithIntrinsicBounds(
                AppCompatResources.getDrawable(this, R.drawable.ic_home_24),
                null,
                null,
                null
            )
        }
    }

    private fun loadProperty() {
        CoroutineScope(Dispatchers.IO).launch {
            val api = base.create(PropertyAPI::class.java)
            val propertyId = intent.extras?.getInt("propertyId")
            val response: Response<Property> = api.show(propertyId!!)
            val property: Property = response.body() ?: throw Exception("Falha ao carregar")


            withContext(Dispatchers.Main) {
                val helper = PropertyHelper(property)
                val amenities = helper.composeAmenities()

                val principalValue = property.priceInformation?.filter { it.description == "value" }

                binding.propertyBeds.text = amenities["bedrooms"]
                binding.propertyBaths.text = amenities["bathrooms"]
                binding.propertyCars.text = amenities["cars"]
                binding.propertyTotalArea.text = amenities["totalArea"]
                binding.pptTotalValue.text = helper.composeTotalValue()
                binding.propertyAddressLine.text = helper.composeAddress()
                binding.propertyTitle.text = property.title
                binding.propertyDescription.text = property.description
                binding.pptType.text = if (property.type == "sale") "Venda" else "Aluguel"
                binding.pptValue.text = principalValue?.get(0)?.value.toString()
                binding.adCode.text = propertyId.toString()

                property.address?.let {
                    setBtnLocationEvents(it)
                    setBtnRouteEvents(it)
                }



                setBtnShareEvents(property)

                val user = property.user

                Picasso.get()
                    .load(user?.imgProfileThumb)
                    .resize(300, 300)
                    .centerInside()
                    .transform(CircleTransform())
                    .into(binding.vendorImage)

                binding.vendorName.text =
                    if (user?.type == "particular") user.name.plus(" ${user.surname}") else user?.name

                if (user?.creci == null) {
                    binding.vendorCreci.visibility = View.GONE
                    binding.lblCreci.visibility = View.GONE
                } else {
                    binding.vendorCreci.text = user.creci
                }

                if (user?.email != null) {
                    setBtnMailEvents(user.email)
                } else {
                    binding.btnMail.isEnabled = false
                }

                if (user?.phone != null) {
                    setBtnPhoneEvents(user.phone)
                    setBtnWhatsappEvents(user.phone)
                } else {

                    binding.btnPhone.isEnabled = false
                    binding.btnWhatsapp.isEnabled = false
                }

                val list = property.gallery?.map { image: Image ->
                    image.thumbnail ?: ""
                }

                val galleryAdapter = ImageAdapter(baseContext) {}
                galleryAdapter.loadData(list!!.toMutableList())
                binding.rvGallery.adapter = galleryAdapter

                val taxesAdapter = TaxesAdapter()
                taxesAdapter.loadData(property.priceInformation!!.toMutableList())
                binding.rvTaxes.adapter = taxesAdapter
                binding.rvTaxes.layoutManager = LinearLayoutManager(baseContext)

            }
        }
    }
}