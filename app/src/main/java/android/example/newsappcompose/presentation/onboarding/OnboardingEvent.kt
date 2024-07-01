package android.example.newsappcompose.presentation.onboarding

sealed class OnboardingEvent {
    data object SaveAppEntry : OnboardingEvent()
}