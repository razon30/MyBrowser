package com.example.razon30.mybrowser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    EditText eturl;
    WebView webView;
    ImageButton go, next, back, makeBokmark, bookmarks, historybutton;
    String urlString, editurlString, backurlstring, nexturlsString;
    WebSettings settings;

    ArrayList<String> list;
    int a;

    DtabaseHelper dbhelper;
    boolean yesbookmark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbhelper = new DtabaseHelper(MainActivity.this);

        eturl = (EditText) findViewById(R.id.eturl);
        webView = (WebView) findViewById(R.id.webview);

        registerForContextMenu(webView);

        go = (ImageButton) findViewById(R.id.imagebutton);
        back = (ImageButton) findViewById(R.id.imagebuttonback);
        next = (ImageButton) findViewById(R.id.imagebuttonnext);
        makeBokmark = (ImageButton) findViewById(R.id.bookmark_the_link);
        bookmarks = (ImageButton) findViewById(R.id.bookmarks);
        historybutton = (ImageButton) findViewById(R.id.history);

        list = new ArrayList<String>();
        list.add("http://google.com");
        a = 0;

        if (a == 0) {

            back.getBackground().setAlpha(100);
            next.getBackground().setAlpha(100);

        }

        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different
                // scales.
                // The progress meter will automatically disappear when we reach
                // 100%
                activity.setTitle("Loading...");
                activity.setProgress(progress * 100);

                if (progress == 100)
                    activity.setTitle(R.string.app_name);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description,
                        Toast.LENGTH_SHORT).show();
            }
        });

        webView.loadUrl("http://google.com");
        list.add("http://google.com");

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Where url is your current url.

                eturl.setText(url);
                editurlString = url;
                backurlstring = url;
                nexturlsString = url;
                list.add(url);

                return super.shouldOverrideUrlLoading(view, url);
            }

        });
        boolean goback = webView.canGoBack();
        if (goback) {
            back.getBackground().setAlpha(250);
        } else {
            back.getBackground().setAlpha(100);
        }
        boolean cannext = webView.canGoForward();
        if (cannext) {
            next.getBackground().setAlpha(250);
        } else {
            next.getBackground().setAlpha(100);
        }

        go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                urlString = eturl.getText().toString();

                if (urlString == null || urlString.length() == 0) {

                    Toast.makeText(getApplicationContext(),
                            "Please enter address", Toast.LENGTH_LONG).show();
                    return;

                }

                String httpstring = urlString.substring(0, 7);
                String httpsString = urlString.substring(0, 8);
                String comString = urlString.substring(urlString.length() - 4,
                        urlString.length());

                if (((httpstring.equals("http://")) || (httpsString
                        .equals("https://")))
                        && ((comString.equals(".com"))
                        || (comString.equals(".org")) || (comString
                        .equals(".net")))) {

                    urlString = urlString;

                } else if (((httpstring != "http://") && (httpsString != "https://"))
                        && ((comString.equals(".com"))
                        || (comString.equals(".org")) || (comString
                        .equals(".net")))) {

                    urlString = "http://" + urlString;

                } else if ((httpstring.equals("http://") || (httpsString
                        .equals("https://")))
                        && ((comString != ".com") && (comString != ".org") && (comString != ".net"))) {

                    urlString = urlString + ".com";

                } else if (((httpstring != "http://") && (httpsString != "https://"))
                        && ((comString != ".com") && (comString != ".org") && (comString != ".net"))) {

                    urlString = "http://" + urlString + ".com";

                }


                webView.loadUrl(urlString);

                boolean goback = webView.canGoBack();
                if (goback) {
                    back.getBackground().setAlpha(250);
                } else {
                    back.getBackground().setAlpha(100);
                }
                boolean cannext = webView.canGoForward();
                if (cannext) {
                    next.getBackground().setAlpha(250);
                } else {
                    next.getBackground().setAlpha(100);
                }

                list.add(urlString);

                a++;

            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean goback = webView.canGoBack();
                if (goback) {
                    back.getBackground().setAlpha(250);

                } else {
                    back.getBackground().setAlpha(100);
                }
                boolean cannext = webView.canGoForward();
                if (cannext) {
                    next.getBackground().setAlpha(250);
                } else {
                    next.getBackground().setAlpha(100);
                }

                boolean can = webView.canGoForward();

                if (can) {

                    webView.goForward();

                    eturl.setText(nexturlsString);

                    back.getBackground().setAlpha(250);
                    String newurlString = webView.getUrl();
                    eturl.setText(newurlString);

                    a++;
                    list.add(newurlString);
                    Toast.makeText(
                            getApplicationContext(),
                            "Url is: " + newurlString + "\na is: " + a
                                    + "\nitem in list of a is: " + list.get(a),
                            Toast.LENGTH_LONG).show();

                } else {
                    return;
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean goback = webView.canGoBack();
                if (goback) {
                    back.getBackground().setAlpha(250);
                } else {
                    back.getBackground().setAlpha(100);
                }
                boolean cannext = webView.canGoForward();
                if (cannext) {
                    next.getBackground().setAlpha(250);
                } else {
                    next.getBackground().setAlpha(100);
                }

                boolean back = webView.canGoBack();
                if (back) {

                    webView.goBack();

                    next.getBackground().setAlpha(250);
                    String newurlString = webView.getUrl();
                    eturl.setText(newurlString);
                    a++;
                    list.add(newurlString);
                    Toast.makeText(
                            getApplicationContext(),
                            "Url is: " + newurlString + "\na is: " + a
                                    + "\nitem in list of a is: " + list.get(a),
                            Toast.LENGTH_LONG).show();

                } else {

                    return;

                }

            }
        });

        makeBokmark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                View view = getLayoutInflater().inflate(
                        R.layout.making_bookmark, null);

                final EditText editurl = (EditText) view.findViewById(R.id.url);

                String address = eturl.getText().toString();

                editurl.setText(address);

                final EditText namEditText = (EditText) view
                        .findViewById(R.id.urlName);

                new AlertDialog.Builder(MainActivity.this)
                        .setView(view)
                        .setTitle("Bookmarks")
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                        String url = editurl.getText()
                                                .toString();
                                        String name = namEditText.getText()
                                                .toString();

                                        browserdata data = new browserdata(
                                                name, url);

                                        long inserted = dbhelper
                                                .insertdata(data);

                                        if (inserted == -1) {

                                            Toast.makeText(MainActivity.this,
                                                    "NO url", Toast.LENGTH_LONG)
                                                    .show();

                                        } else if (inserted >= 0) {

                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "Bookmarks added",
                                                    Toast.LENGTH_LONG).show();
                                            yesbookmark = true;

                                        }

                                    }
                                })
                        .setNegativeButton("No,Go Back",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //
                                        dialog.cancel();
                                    }
                                }).show();

            }
        });

        historybutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                View view = getLayoutInflater().inflate(R.layout.history, null);

                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) view
                        .findViewById(R.id.autocompletetextview);

                final ListView historylListView = (ListView) view
                        .findViewById(R.id.listView1);

                // registerForContextMenu(historylListView);
                // historylListView.notify();

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this, android.R.layout.simple_list_item_1,
                        list);

                historylListView.setAdapter(adapter);
                autoCompleteTextView.setThreshold(1);
                autoCompleteTextView.setAdapter(adapter);

                autoCompleteTextView
                        .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent,
                                                    View view, int position, long id) {

                                webView.loadUrl(list.get(position));
                                eturl.setText(list.get(position));

                            }
                        });

                AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(
                        MainActivity.this);

                historylListView
                        .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent,
                                                    View view, int position, long id) {
                                // TODO Auto-generated method stub

                                String name = (String) historylListView
                                        .getItemAtPosition(position);

                                webView.loadUrl(name);
                                // list.add(name);

                                // adapter.notifyDataSetChanged();
                                // historylListView.setAdapter(adapter);

                            }
                        });

                historylListView
                        .setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                            @Override
                            public boolean onItemLongClick(
                                    AdapterView<?> parent, View view,
                                    int position, long id) {
                                adapter.remove(list.get(position));

                                adapter.setNotifyOnChange(true);
                                historylListView.invalidateViews();
                                // historylListView.notify();

                                return false;
                            }
                        });

                builderAlertDialog
                        .setTitle("History :")
                        .setView(view)

                        .setPositiveButton("Clear History",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                        list.clear();

                                    }
                                }).show();

            }
        });

        bookmarks.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final ArrayList<String> listitemArrayList = new ArrayList<String>();

                View view = getLayoutInflater().inflate(
                        R.layout.show_bookmarks, null);

                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) view
                        .findViewById(R.id.bookmarkautocompletetextview);

                final ListView bookmarkListView = (ListView) view
                        .findViewById(R.id.bookmarklistView1);

                // registerForContextMenu(historylListView);
                // historylListView.notify();

                final ArrayList<browserdata> arraylist = dbhelper
                        .searchresult();

                if (arraylist.size() > 0 || arraylist != null) {
                    for (int i = 0; i < arraylist.size(); i++) {
                        String urlnameString = arraylist.get(i).getName();
                        String urlString = arraylist.get(i).getLink();

                        if (urlnameString.length() == 0
                                || urlnameString == null) {
                            listitemArrayList.add(urlString);
                        } else {
                            listitemArrayList.add(urlnameString);
                        }

                    }

                } else {

                    Toast.makeText(MainActivity.this, "No data found",
                            Toast.LENGTH_LONG).show();
                    return;

                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this, android.R.layout.simple_list_item_1,
                        listitemArrayList);

                bookmarkListView.setAdapter(adapter);
                autoCompleteTextView.setThreshold(1);
                autoCompleteTextView.setAdapter(adapter);

                autoCompleteTextView
                        .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent,
                                                    View view, int position, long id) {

                                String urlString = arraylist.get(position)
                                        .getLink();

                                webView.loadUrl(urlString);
                                eturl.setText(urlString);
                                // do somethong
                            }
                        });

                AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(
                        MainActivity.this);

                bookmarkListView
                        .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent,
                                                    View view, int position, long id) {
                                // TODO Auto-generated method stub

                                String urlString = arraylist.get(position)
                                        .getLink();

                                webView.loadUrl(urlString);
                                eturl.setText(urlString);
                                list.add(urlString);

                                // do somethong
                            }
                        });

                bookmarkListView
                        .setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                            @Override
                            public boolean onItemLongClick(
                                    AdapterView<?> parent, View view,
                                    final int position, long id) {

                                final String address = (String) bookmarkListView
                                        .getItemAtPosition(position);

                                new AlertDialog.Builder(MainActivity.this)
                                        .setMessage(
                                                "Do you want to delete the Address?")
                                        .setPositiveButton(
                                                "Yes",
                                                new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(
                                                            DialogInterface dialog,
                                                            int which) {
                                                        // TODO Auto-generated
                                                        // method stub

                                                        int id1 = dbhelper
                                                                .delete(address);

                                                        adapter.remove(listitemArrayList
                                                                .get(position));

                                                        adapter.setNotifyOnChange(true);
                                                        bookmarkListView
                                                                .invalidateViews();

                                                    }
                                                })
                                        .setNegativeButton(
                                                "No, Go Back",
                                                new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(
                                                            DialogInterface dialog,
                                                            int which) {

                                                        dialog.cancel();
                                                    }
                                                }).show();

                                return false;
                            }
                        });

                builderAlertDialog.setTitle("Bookmarks :").setView(view)

                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // do somethong

                            }
                        }).show();
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.yahoo) {
            eturl.setText("https://www.yahoo.com");
            webView.loadUrl("https://www.yahoo.com");
        }
        if (id == R.id.google) {
            eturl.setText("http://google.com");
            webView.loadUrl("http://google.com");
        }
        if (id == R.id.amazon) {
            eturl.setText("http://www.amazon.com");
            webView.loadUrl("http://www.amazon.com");
        }
        if (id == R.id.bing) {
            eturl.setText("http://www.bing.com");
            webView.loadUrl("http://www.bing.com");

        }
        if (id == R.id.ask) {
            eturl.setText("http://www.ask.com");
            webView.loadUrl("http://www.ask.com");

        }
        if (id == R.id.ekhanei) {
            eturl.setText("http://www.ekhanei.com");
            webView.loadUrl("http://www.ekhanei.com");

        }
        if (id == R.id.ebay) {
            eturl.setText("http://www.ebay.com");
            webView.loadUrl("http://www.ebay.com");

        }
        if (id == R.id.twitter) {
            eturl.setText("http://www.twitter.com");
            webView.loadUrl("http://www.twitter.com");

        }
        if (id == R.id.wiki) {
            eturl.setText("http://bn.wikipedia.org/wiki.com");
            webView.loadUrl("http://bn.wikipedia.org/wiki.com");

        }
        if (id == R.id.youtube) {
            eturl.setText("https://www.youtube.com");
            webView.loadUrl("https://www.youtube.com");

        }
        if (id == R.id.ask_fm) {
            eturl.setText("http://ask.fm/login");
            webView.loadUrl("http://ask.fm/login");

        }
        if (id == R.id.facebook) {
            eturl.setText("http://m.facebook.com");
            webView.loadUrl("http://m.facebook.com");

            Toast.makeText(MainActivity.this, "clicked facebook",
                    Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Actions").setHeaderIcon(R.drawable.clearhistory);
        menu.add(0, v.getId(), 0, "Open");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getTitle() == "Open") {
            Toast.makeText(this, "Action 1 invoked", Toast.LENGTH_SHORT).show();
            webView.loadUrl(item.toString());
        } else if (item.getTitle() == "Delete") {
            Toast.makeText(this, "Action 2 invoked", Toast.LENGTH_SHORT).show();

            WebBackForwardList list = webView.copyBackForwardList();

        } else {
            return false;
        }
        return true;
    }


}
