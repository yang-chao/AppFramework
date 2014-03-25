package com.stef.framework.base.fragment;

import com.stef.framework.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaseFragment extends Fragment {

	// UI references.
	private View mContentContainer;
	private View mProgressContainer;
	private View mProgressView;
	private TextView mProgressMessageView;
	private View mEmptyContainer;
	private ProgressDialog mProgressDialog;
	
	private boolean mContentShown = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		RelativeLayout root = (RelativeLayout) inflater.inflate(R.layout.base_fragment, container, false);
		
		RelativeLayout.LayoutParams lp;
		
		mContentContainer = onCreateFragmentView(inflater, container, savedInstanceState);
		if(mContentContainer != null) {
			lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			root.addView(mContentContainer, lp);
		}
		
		mProgressContainer = onCreateProgressView(inflater, container);
		if(mProgressContainer != null) {
			lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			root.addView(mProgressContainer, lp);
			
			mProgressView = mProgressContainer.findViewById(R.id.progress_status);
			mProgressMessageView = (TextView) mProgressContainer.findViewById(R.id.status_message);
		}
		
		mEmptyContainer = onCreateEmptyView(inflater, container);
		if(mEmptyContainer != null) {
			lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			mEmptyContainer.setVisibility(View.GONE);
			lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			root.addView(mEmptyContainer, lp);
		}
		
		return root;
	}
	
	/**
	 * Called to have the fragment instantiate its user interface view. This is optional.
	 * 
	 * @return Return the View for the fragment's UI, or null.
	 */
	public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return null;
	}
	
	/**
	 * Called to have the fragment instantiate its progress interface view. This is optional.
	 * 
	 * @param inflater
	 * @param container
	 * @return
	 */
	public View onCreateProgressView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.base_progress_layout, container, false);
	}
	
	/**
	 * Called to have the fragment instantiate its empty interface view. This is optional.
	 * 
	 * @param inflater
	 * @param container
	 * @return
	 */
	public View onCreateEmptyView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.base_empty_layout, container, false);
	}
	
	public void setContentShown(boolean shown) {
		setContentShown(shown, true);
	}
	
	public void setContentShownNoAnimation(boolean shown) {
		setContentShown(shown, false);
	}
	
	public void setContentShown(boolean shown, boolean animate) {
		if (mContentContainer == null) {
			throw new IllegalStateException("Can't be used without a content view");
		}
		if (mContentShown == shown) {
			return;
		}
		mContentShown = shown;
		if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
                mContentContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
            } else {
                mProgressContainer.clearAnimation();
                mContentContainer.clearAnimation();
            }
            mEmptyContainer.setVisibility(View.GONE);
            mProgressContainer.setVisibility(View.GONE);
            mContentContainer.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
                mContentContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
            } else {
                mProgressContainer.clearAnimation();
                mContentContainer.clearAnimation();
            }
            mEmptyContainer.setVisibility(View.GONE);
            mProgressContainer.setVisibility(View.VISIBLE);
            mContentContainer.setVisibility(View.GONE);
        }
	}
	
	/**
	 * Shows or hides the progress UI.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if(mProgressView == null || !isAdded()) {
			return;
		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mProgressView.setVisibility(View.VISIBLE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
		}
	}
	
	/**
	 * 设置显示空view的提示信息
	 * 
	 * @param message
	 */
	public void setEmptyMessage(String message) {
		if (mEmptyContainer != null) {
			if (mEmptyContainer instanceof TextView) {
				((TextView) mEmptyContainer.findViewById(R.id.empty_tip)).setText(message);
			}
		}
	}
	
	/**
	 * 设置显示空view的提示信息
	 * 
	 * @param message
	 */
	public void setEmptyMessage(int resId) {
		if (mEmptyContainer != null) {
			if (mEmptyContainer instanceof TextView) {
				((TextView) mEmptyContainer.findViewById(R.id.empty_tip)).setText(resId);
			}
		}
	}
	
	/**
	 * Shows or hides the empty UI.
	 */
	public void showEmpty(boolean show) {
		if (!isAdded() || mEmptyContainer == null || mProgressContainer == null || mContentContainer == null) {
			return;
		}
		
		if (show) {
			mContentContainer.setVisibility(View.GONE);
			mProgressContainer.setVisibility(View.GONE);
			mEmptyContainer.setVisibility(View.VISIBLE);
		} else {
			mContentContainer.setVisibility(View.VISIBLE);
			mProgressContainer.setVisibility(View.VISIBLE);
			mEmptyContainer.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 设置loading时的提示文字
	 * 
	 * @param message
	 */
	public void setProgressMessage(String message) {
		if(mProgressMessageView != null) {
			mProgressMessageView.setText(message);
		}
	}
	
	/**
	 * 设置loading时的提示文字
	 * 
	 * @param resId
	 */
	public void setProgressMessage(int resId) {
		if(mProgressMessageView != null) {
			mProgressMessageView.setText(resId);
		}
	}
	
	/**
	 * 是否正在加载
	 * 
	 * @return
	 */
	public boolean isOnProgressing() {
		return mProgressView.getVisibility() == View.VISIBLE ? true : false;
	}
	
	/**
	 * Show progress dialog
	 * 
	 * @param message
	 */
	public void showProgressDialog(String message) {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
		}
		mProgressDialog.setMessage(message);
		mProgressDialog.show();
	}
	
	/**
	 * Show progress dialog
	 * 
	 * @param resId
	 */
	public void showProgressDialog(int resId) {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setCanceledOnTouchOutside(false);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
		}
		mProgressDialog.setMessage(getString(resId));
		mProgressDialog.show();
	}
	
	/**
	 * Dismiss progress dialog
	 */
	public void dismissProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
	
	/**
	 * 是否正在显示空View
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return mEmptyContainer.getVisibility() == View.VISIBLE ? true : false;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
		mContentShown = false;
		mContentContainer = null;
		mProgressContainer = null;
		mProgressMessageView = null;
		mProgressView = null;
		mEmptyContainer = null;
	}
	
}
