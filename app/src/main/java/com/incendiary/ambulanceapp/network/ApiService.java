package com.incendiary.ambulanceapp.network;

import com.incendiary.ambulanceapp.data.model.Ambulance;
import com.incendiary.ambulanceapp.data.model.BaseResponse;
import com.incendiary.ambulanceapp.data.model.BookingResponse;
import com.incendiary.ambulanceapp.data.model.EditProfileResponse;
import com.incendiary.ambulanceapp.data.model.HistoryBooking;
import com.incendiary.ambulanceapp.data.model.Profile;
import com.incendiary.ambulanceapp.data.model.RekamMedis;
import com.incendiary.ambulanceapp.data.model.booking.BookingStatus;
import com.incendiary.ambulanceapp.data.model.comment.Comment;
import com.incendiary.ambulanceapp.data.model.common.ApiResponse;
import com.incendiary.ambulanceapp.data.model.detail.AmbulanceDetail;
import com.incendiary.ambulanceapp.data.model.detail.BidanDetail;
import com.incendiary.ambulanceapp.data.model.detail.DoctorDetail;
import com.incendiary.ambulanceapp.data.model.drupadi.Bidan;
import com.incendiary.ambulanceapp.data.model.drupadi.Doctor;
import com.incendiary.ambulanceapp.data.model.family.Family;
import com.incendiary.ambulanceapp.data.model.nik.Nik;
import com.incendiary.ambulanceapp.data.model.notification.SemarNotification;
import com.incendiary.ambulanceapp.data.model.patient.Layanan;
import com.incendiary.ambulanceapp.data.model.poi.Poi;
import com.incendiary.ambulanceapp.data.model.poi.PoiCategory;
import com.incendiary.ambulanceapp.data.model.poi.atmberas.AtmBerasInfo;
import com.incendiary.ambulanceapp.data.model.poi.hospital.AvailableRoom;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.data.model.report.ReportCategory;
import com.incendiary.ambulanceapp.data.model.report.ReportFeedback;
import com.incendiary.ambulanceapp.data.model.tours.TourPlace;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

 /* --------------------------------------------------- */
  /* > Account */
  /* --------------------------------------------------- */

    @FormUrlEncoded
    @POST("apps_svc.php?type=login")
    Observable<ApiResponse<Profile>>
    login(@Field("username") String username,
          @Field("password") String password);

    @FormUrlEncoded
    @POST("apps_svc.php?type=registerUser")
    Observable<ApiResponse>
    register(@Field("username") String username,
             @Field("email") String email,
             @Field("nama_lengkap") String name,
             @Field("password") String pass,
             @Field("nik") String noKtp,
             @Field("no_hp") String telp,
             @Field("nama_ibu_kandung") String motherName,
             @Field("tgl_lahir") String dob,
             @Field("status_domisili") int domisili);

    @FormUrlEncoded
    @POST("apps_svc.php?type=resetPassword")
    Observable<ApiResponse>
    resetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("apps_svc.php?type=registerDeviceToken")
    Observable<ApiResponse>
    registerDevice(@Field("device_token") String email);

    @FormUrlEncoded
    @POST("apps_svc.php?type=editUserProfile")
    Observable<EditProfileResponse>
    editProfile(@Field("user_id") String userId,
                @Field("keycode") String keycode,
                @Field("username") String username,
                @Field("email") String email,
                @Field("nama_lengkap") String name,
                @Field("password") String password,
                @Field("nik") String noKtp,
                @Field("no_hp") String telp,
                @Field("nama_ibu_kandung") String motherName,
                @Field("tgl_lahir") String dob,
                @Field("user_img") String userImage);

    @FormUrlEncoded
    @POST("apps_svc.php?type=editUserProfile")
    Observable<EditProfileResponse>
    editDomicile(@Field("user_id") String userId,
                 @Field("keycode") String keycode,
                 @Field("nik") String nik,
                 @Field("status_domisili") int statusDomisili);

    @FormUrlEncoded
    @POST("apps_svc.php?type=editUserProfile")
    Observable<EditProfileResponse>
    setPushNotificationStatus(@Field("push_notif") String isEnable);

    @GET("apps_svc.php?type=getRekamMedis")
    Observable<ApiResponse<List<RekamMedis>>>
    getRekamMedis(@Query("user_id") String userId);

    @GET("apps_svc.php?type=getRiwayatPanggilan")
    Observable<ApiResponse<List<HistoryBooking>>>
    getHistoryBooking(@Query("user_id") String userId);

    /* --------------------------------------------------- */
    /* > Account - Verification */
    /* --------------------------------------------------- */

    @FormUrlEncoded
    @POST("apps_svc.php?type=verifikasiNIK")
    Observable<ApiResponse<Nik>> verifyNik(
            @Field("nik") String nik);

    /* --------------------------------------------------- */
    /* > Account - Family */
    /* --------------------------------------------------- */

    @FormUrlEncoded
    @POST("apps_svc.php?type=hapusAnggotaKeluarga")
    Observable<ApiResponse> deleteFamily(
            @Field("user_id") String userId,
            @Field("keycode") String keycode,
            @Field("anggota_id") String anggotaId);

    @FormUrlEncoded
    @POST("apps_svc.php?type=tambahAnggotaKeluarga")
    Observable<ApiResponse> addFamily(
            @Field("user_id") String userId,
            @Field("keycode") String keycode,
            @Field("nik") String nik,
            @Field("nama_lengkap") String nama,
            @Field("tgl_lahir") String dob, //yyyy-MM-dd
            @Field("nama_ibu_kandung") String motherName,
            @Field("status_keluarga") String status);

    @FormUrlEncoded
    @POST("apps_svc.php?type=editAnggotaKeluarga")
    Observable<ApiResponse> editFamily(
            @Field("user_id") String userId,
            @Field("keycode") String keycode,
            @Field("anggota_id") String anggotaId,
            @Field("nik") String nik,
            @Field("nama_lengkap") String nama,
            @Field("tgl_lahir") String dob, //yyyy-MM-dd
            @Field("nama_ibu_kandung") String motherName,
            @Field("status_keluarga") String status);

    @GET("apps_svc.php?type=getListAnggotaKeluarga")
    Observable<ApiResponse<List<Family>>> getFamilies(@Query("user_id") String userId);

    /* --------------------------------------------------- */
    /* > SOS */
    /* --------------------------------------------------- */

    @FormUrlEncoded
    @POST("apps_svc.php?type=emergencyCall")
    Observable<ApiResponse> emergencyCall(
            @Field("user_id") String userId,
            @Field("keycode") String keycode,
            @Field("lat") String latitude,
            @Field("lng") String longitude,
            @Field("sos_type") String sosType);

    /* --------------------------------------------------- */
    /* > Marker */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=getAmbulance")
    Observable<ApiResponse<List<Ambulance>>>
    getAmbulanceMarkers(@Query("user_id") String userId);

    @GET("apps_svc.php?type=getDokter")
    Observable<ApiResponse<List<Doctor>>>
    getDoctorMarkers(@Query("user_id") String userId);

    @GET("apps_svc.php?type=getBidan")
    Observable<ApiResponse<List<Bidan>>>
    getBidanMarkers(@Query("user_id") String userId);

    /* --------------------------------------------------- */
    /* > Booking */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=getStatusBookingUser")
    Observable<ApiResponse<BookingStatus>>
    getBookingStatus(@Query("user_id") String userId);

  /* --------------------------------------------------- */
  /* > Booking Ambulance */
  /* --------------------------------------------------- */

    @FormUrlEncoded
    @POST("apps_svc.php?type=bookingAmbulance")
    Observable<BookingResponse>
    bookingAmbulance(
            @Field("user_id") String userId,
            @Field("user_lat") String lat,
            @Field("user_lng") String lng);

    @FormUrlEncoded
    @POST("apps_svc.php?type=completeBookingAmbulance")
    Observable<BaseResponse>
    completeBooking(
            @Field("user_id") String userId,
            @Field("booking_id") String bookingId);

    @FormUrlEncoded
    @POST("apps_svc.php?type=cancelBookingAmbulance")
    Observable<BaseResponse>
    cancelBooking(
            @Field("user_id") String userId,
            @Field("booking_id") String bookingId);

  /* --------------------------------------------------- */
  /* > Booking Doctor */
  /* --------------------------------------------------- */

    @FormUrlEncoded
    @POST("apps_svc.php?type=bookingDokter")
    Observable<BookingResponse>
    bookingDoctor(@Field("user_id") String userId,
                  @Field("user_lat") String lat,
                  @Field("user_lng") String lng);

    @FormUrlEncoded
    @POST("apps_svc.php?type=completeBookingDokter")
    Observable<BaseResponse>
    completeBookingDoctor(@Field("user_id") String userId,
                          @Field("booking_id") String bookingId);

    @FormUrlEncoded
    @POST("apps_svc.php?type=cancelBookingDokter")
    Observable<BaseResponse> cancelBookingDoctor(
            @Field("user_id") String userId,
            @Field("booking_id") String bookingId
    );

  /* --------------------------------------------------- */
  /* > Booking Bidan */
  /* --------------------------------------------------- */

    @FormUrlEncoded
    @POST("apps_svc.php?type=bookingBidan")
    Observable<BookingResponse> bookingBidan(
            @Field("user_id") String userId,
            @Field("user_lat") String lat,
            @Field("user_lng") String lng
    );

    @FormUrlEncoded
    @POST("apps_svc.php?type=completeBookingBidan")
    Observable<BaseResponse> completeBookingBidan(
            @Field("user_id") String userId,
            @Field("booking_id") String bookingId
    );

    @FormUrlEncoded
    @POST("apps_svc.php?type=cancelBookingBidan")
    Observable<BaseResponse> cancelBookingBidan(
            @Field("user_id") String userId,
            @Field("booking_id") String bookingId
    );

    /* --------------------------------------------------- */
    /* > Polling Detail Data */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=getDetailBidan")
    Observable<ApiResponse<BidanDetail>> getDetailBidan(
            @Query("user_id") String userId,
            @Query("keycode") String keycode,
            @Query("booking_id") String bookingId);


    @GET("apps_svc.php?type=getDetailDokter")
    Observable<ApiResponse<DoctorDetail>> getDetailDoctor(
            @Query("user_id") String userId,
            @Query("keycode") String keycode,
            @Query("booking_id") String bookingId);


    @GET("apps_svc.php?type=getDetailAmbulance")
    Observable<ApiResponse<AmbulanceDetail>> getDetailAmbulance(
            @Query("user_id") String userId,
            @Query("keycode") String keycode,
            @Query("booking_id") String bookingId);

    /* --------------------------------------------------- */
    /* > Patient Data */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=getJenisLayananKesehatan")
    Observable<ApiResponse<List<Layanan>>> getLayanan(
            @Query("user_id") String userId);

    @FormUrlEncoded
    @POST("apps_svc.php?type=isiDataPasienBidan")
    Observable<ApiResponse> insertBidanPatientData(
            @Field("user_id") String userId,
            @Field("keycode") String keycode,
            @Field("booking_id") String bookingId,
            @Field("nik_pasien") String nik,
            @Field("nama_pasien") String patientName,
            @Field("layanan_id") String serviceId);

    @FormUrlEncoded
    @POST("apps_svc.php?type=isiDataPasienDokter")
    Observable<ApiResponse> insertDoctorPatientData(
            @Field("user_id") String userId,
            @Field("keycode") String keycode,
            @Field("booking_id") String bookingId,
            @Field("nik_pasien") String nik,
            @Field("nama_pasien") String patientName,
            @Field("layanan_id") String serviceId);

    @FormUrlEncoded
    @POST("apps_svc.php?type=isiDataPasienAmbulance")
    Observable<ApiResponse> insertAmulancePatientData(
            @Field("user_id") String userId,
            @Field("keycode") String keycode,
            @Field("booking_id") String bookingId,
            @Field("nik_pasien") String nik,
            @Field("nama_pasien") String patientName,
            @Field("layanan_id") String serviceId);

    /* --------------------------------------------------- */
    /* > Destinasi Wisata */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=getDestinasiWisata")
    Observable<ApiResponse<List<TourPlace>>> getTourPlaces(
            @Query("user_id") String userId,
            @Query("kategori") String kategori,
            @Query("user_lat") String userLat,
            @Query("user_lng") String userLong);

    @GET("apps_svc.php?type=searchDestinasiWisata")
    Observable<ApiResponse<List<TourPlace>>> searchTourPlaces(
            @Query("user_id") String userId,
            @Query("search") String search,
            @Query("user_lat") String latitude,
            @Query("user_lng") String longitude);

    /* --------------------------------------------------- */
    /* > Lokasi Penting */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=getKategoriLokasiPenting")
    Observable<ApiResponse<List<PoiCategory>>> getCatgoryLokasiPenting(
            @Query("user_id") String userId);

    @GET("apps_svc.php?type=getLokasiPenting")
    Observable<ApiResponse<List<Poi>>> getLokasiPenting(
            @Query("user_id") String userId,
            @Query("kategori_id") String kategoriId);

    @GET("apps_svc.php?type=getKetersediaanKamar")
    Observable<ApiResponse<AvailableRoom>> getAvailableRoom(@Query("lokasi_id") String locationId);

    @GET("apps_svc.php?type=getInfoAtmBeras")
    Observable<ApiResponse<AtmBerasInfo>> getAtmBerasInfo(@Query("lokasi_id") String locationId);

    /* --------------------------------------------------- */
    /* > Comment */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=getComment")
    Observable<ApiResponse<List<Comment>>> getComments(@Query("laporan_id") String laporanId);

    @FormUrlEncoded
    @POST("apps_svc.php?type=postComment")
    Observable<ApiResponse> postComment(
            @Field("user_id") String userId,
            @Field("keycode") String keycode,
            @Field("laporan_id") String laporanId,
            @Field("comment") String comment);

    @GET("apps_svc.php?type=getMyComment")
    Observable<ApiResponse<List<Comment>>> getMyComments();

    /* --------------------------------------------------- */
    /* > Notification */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=getUserNotification")
    Observable<ApiResponse<List<SemarNotification>>> getNotifications();

    @GET("apps_svc.php?type=openNotification")
    Observable<ApiResponse> openNotification(@Query("notificatioN_id") String notificationId);

    /* --------------------------------------------------- */
    /* > Report */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=getLaporanTerpopuler")
    Observable<ApiResponse<List<Report>>> getPopularReports();

    @GET("apps_svc.php?type=getRiwayatLaporan")
    Observable<ApiResponse<List<Report>>> getHistoryReports(
            @Query("page") int page,
            @Query("status_laporan") String status);

    @GET("apps_svc.php?type=getBookmarkedLaporan")
    Observable<ApiResponse<List<Report>>> getBookmarkedReports(
            @Query("page") int page);

    @GET("apps_svc.php?type=getLaporanById")
    Observable<ApiResponse<Report>> getReportById(@Query("laporan_id") String reportId);

    @GET("apps_svc.php?type=getListLaporan")
    Observable<ApiResponse<List<Report>>> getReports(
            @Query("page") int page);


    /* --------------------------------------------------- */
    /* > Like Report */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=likeLaporan")
    Observable<ApiResponse> likeReport(@Query("laporan_id") String reportId);

    @GET("apps_svc.php?type=unlikeLaporan")
    Observable<ApiResponse> unlikeReport(@Query("laporan_id") String reportId);

    /* --------------------------------------------------- */
    /* > Bookmark Report */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=bookmarkLaporan")
    Observable<ApiResponse> bookmarkReport(@Query("laporan_id") String reportId);

    @GET("apps_svc.php?type=unbookmarkLaporan")
    Observable<ApiResponse> unbookmarkReport(@Query("laporan_id") String reportId);

    /* --------------------------------------------------- */
    /* > Report Status */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=getStatusLaporan")
    Observable<ApiResponse<ReportFeedback>> getStatusLaporan(
            @Query("laporan_id") String reportId);

    /* --------------------------------------------------- */
    /* > Submit Report */
    /* --------------------------------------------------- */

    @GET("apps_svc.php?type=getKategoriLaporan")
    Observable<ApiResponse<List<ReportCategory>>> getReportCategories();

    @FormUrlEncoded
    @POST("apps_svc.php?type=submitLaporan")
    Observable<ApiResponse> submitReport(
            @Field("user_id") String userId,
            @Field("keycode") String keycode,
            @Field("kategori_id") String categoryId,
            @Field("judul") String judul,
            @Field("keterangan") String keterangan,
            @Field("lat") String lat,
            @Field("lng") String lang,
            @Field("foto_laporan") String photo
    );
}
