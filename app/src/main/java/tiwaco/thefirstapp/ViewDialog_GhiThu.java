package tiwaco.thefirstapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.Database.SPData;

/**
 * Created by TUTRAN on 15/06/2018.
 */

public class ViewDialog_GhiThu {
     SPData spdata;
    DuongDAO duongDAO;

    public void showDialog(final Activity activity, String msg, final  int loai){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_ghithu);
        spdata = new SPData(activity);
        duongDAO = new DuongDAO(activity);
        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton_ghi = (Button) dialog.findViewById(R.id.btn_dialog_ghi);
        dialogButton_ghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if(loai ==1){

                String maduong = spdata.getDataDuongDangGhiTrongSP();


                if (maduong.equals("")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage(R.string.start_chuacoduongdeghinuoc);
                    // thiết lập nội dung cho dialog
                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent myIntent2 = new Intent(activity, ListActivity.class);
                            activity.startActivity(myIntent2);
                            activity.finish();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // tạo dialog
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                } else {
                    Log.e("maduong", maduong);
                    String tenduong = duongDAO.getTenDuongTheoMa(maduong);
                    String mess = "Bạn có muốn tiếp tục ghi nước đường " + maduong + "." + tenduong + " không?";
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage(mess);
                    // thiết lập nội dung cho dialog
                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();


                            Intent myIntent = new Intent(activity, MainActivity.class);
                            //Intent myIntent = new Intent(this, LoadFromServerActivity.class);
                            myIntent.putExtra("MauLoadGhiThu", "1");
                            activity.startActivity(myIntent);


                            //  Log.e("Bien index duong", String.valueOf(Bien.bien_index_duong));
                            //  spdata.luuDataIndexDuongDangGhiTrongSP(Bien.bien_index_duong);
                            // Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                            // khởi tạo dialog
                            alertDialogBuilder.setMessage("Bạn có muốn ghi nước đường khác không?");
                            // thiết lập nội dung cho dialog
                            alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    Intent myIntent2 = new Intent(activity, ListActivity.class);
                                    activity.startActivity(myIntent2);
                                }
                            });

                            alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();


                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // tạo dialog
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // tạo dialog
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }


                }
                else{
                    Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
                    Intent myIntent2 = new Intent(activity, ListActivity.class);
                    activity.startActivity(myIntent2);
                }





            }
        });

        Button dialogButton_thu = (Button) dialog.findViewById(R.id.btn_dialog_thu);
        dialogButton_thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if(loai ==1) {


                    String maduong = spdata.getDataDuongDangThuTrongSP();


                    if (maduong.equals("")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage(R.string.start_chuacoduongdethunuoc);
                        // thiết lập nội dung cho dialog
                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent myIntent2 = new Intent(activity, ListThuActivity.class);
                                activity.startActivity(myIntent2);

                            }
                        });

                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    } else {
                        Log.e("maduong", maduong);
                        String tenduong = duongDAO.getTenDuongTheoMa(maduong);
                        String mess = "Bạn có muốn tiếp tục thu tiền nước đường " + maduong + "." + tenduong + " không?";
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage(mess);
                        // thiết lập nội dung cho dialog
                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();


                                Intent myIntent = new Intent(activity, MainThuActivity.class);
                                //Intent myIntent = new Intent(this, LoadFromServerActivity.class);
                                myIntent.putExtra("MauLoadGhiThu", "1");
                                activity.startActivity(myIntent);


                                //  Log.e("Bien index duong", String.valueOf(Bien.bien_index_duong));
                                //  spdata.luuDataIndexDuongDangGhiTrongSP(Bien.bien_index_duong);
                                // Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
                            }
                        });

                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Bạn có muốn thu tiền nước đường khác không?");
                                // thiết lập nội dung cho dialog
                                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        Intent myIntent2 = new Intent(activity, ListThuActivity.class);
                                        activity.startActivity(myIntent2);
                                    }
                                });

                                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();


                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();

                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();

                    }
                }
                else{
                    Bien.selected_item = spdata.getDataIndexDuongDangThuTrongSP();
                    Intent myIntent2 = new Intent(activity, ListThuActivity.class);
                    activity.startActivity(myIntent2);
                }



            }
        });

        Button dialogButton_thoat = (Button) dialog.findViewById(R.id.btn_thoat);
        dialogButton_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();



            }
        });


        dialog.show();

    }
}
